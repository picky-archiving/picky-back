package com.example.pickyback.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.pickyback.global.dto.ApiResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 예외 처리 (BaseException)
	 * 도메인별 ErrorCode가 여기서 처리됨
	 */
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.warn("BaseException: code={}, message={}",
				errorCode.getStatus().value(), errorCode.getMessage());

		return ResponseEntity
				.status(errorCode.getStatus())
				.body(ApiResponse.fail(errorCode.getStatus(), errorCode.getMessage()));
	}

	/**
	 * 400 Bad Request - 클라이언트 요청 오류
	 * - DTO 검증 실패 (@Valid)
	 * - JSON 파싱 실패
	 * - 필수 파라미터 누락
	 * - 제약 조건 위반
	 */
	@ExceptionHandler({
			MethodArgumentNotValidException.class,
			HttpMessageNotReadableException.class,
			MissingServletRequestParameterException.class,
			ConstraintViolationException.class
	})
	protected ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception e) {
		log.warn("Bad request: {}", e.getMessage());

		String message = CommonErrorCode.BAD_REQUEST.getMessage();

		// 유효성 검증 실패 시 상세 메시지 제공
		if (e instanceof MethodArgumentNotValidException validEx) {
			message = validEx.getBindingResult().getFieldErrors().stream()
					.findFirst()
					.map(error -> error.getField() + ": " + error.getDefaultMessage())
					.orElse(message);
		}

		return ResponseEntity
				.status(CommonErrorCode.BAD_REQUEST.getStatus())
				.body(ApiResponse.badRequest(message));
	}

	/**
	 * 404 Not Found - 리소스를 찾을 수 없음
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleNotFound(NoHandlerFoundException e) {
		log.warn("Resource not found: {}", e.getRequestURL());

		return ResponseEntity
				.status(CommonErrorCode.NOT_FOUND.getStatus())
				.body(ApiResponse.notFound(CommonErrorCode.NOT_FOUND.getMessage()));
	}

	/**
	 * 405 Method Not Allowed - 지원하지 않는 HTTP 메서드
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
			HttpRequestMethodNotSupportedException e) {
		log.warn("Method not supported: {}", e.getMethod());

		return ResponseEntity
				.status(e.getStatusCode())
				.body(ApiResponse.fail(
						org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED,
						"지원하지 않는 HTTP 메서드입니다: " + e.getMethod()));
	}

	/**
	 * 500 Internal Server Error - 예상치 못한 서버 오류
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		log.error("Unexpected error occurred", e);

		return ResponseEntity
				.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getStatus())
				.body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
	}
}