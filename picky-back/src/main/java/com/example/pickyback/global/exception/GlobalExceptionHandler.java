package com.example.pickyback.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.pickyback.global.dto.ApiResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 0. 비즈니스용 BaseException
	 *    (도메인 ErrorCode 들이 전부 여기서 처리됨)
	 */
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.warn("BaseException: {}", errorCode.getMessage(), e);

		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.fail(errorCode.getMessage(), null));
	}

	/**
	 * 1. 클라이언트가 요청을 잘못 보낸 경우 (400)
	 *    - DTO 검증 실패(@Valid)
	 *    - JSON 파싱 실패
	 *    - 필수 파라미터 누락
	 *    - 지원하지 않는 HTTP 메서드 등
	 */
	@ExceptionHandler({
		MethodArgumentNotValidException.class,
		HttpMessageNotReadableException.class,
		MissingServletRequestParameterException.class,
		ConstraintViolationException.class,
		HttpRequestMethodNotSupportedException.class
	})
	protected ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception e) {
		ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
		log.warn("Bad request: {}", e.getMessage(), e);

		return ResponseEntity
			.status(errorCode.getStatus())               // 400
			.body(ApiResponse.fail(errorCode.getMessage(), null));
	}

	/**
	 * 2. 그 외 모든 예외 = 서버 상태 문제 (500)
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
		log.error("Unexpected error", e);

		return ResponseEntity
			.status(errorCode.getStatus())               // 500
			.body(ApiResponse.fail(errorCode.getMessage(), null));
	}
}
