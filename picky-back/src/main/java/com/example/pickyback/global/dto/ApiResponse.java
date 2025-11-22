package com.example.pickyback.global.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private final int status;
	private final boolean success;
	private final String message;
	private final T data;
	private final LocalDateTime timestamp;

	private ApiResponse(int status, boolean success, String message, T data) {
		this.status = status;
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}

	// ==================== Success Responses ====================

	/**
	 * 200 OK - 조회 성공
	 */
	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(HttpStatus.OK.value(), true, "요청이 성공했습니다.", data);
	}

	public static <T> ApiResponse<T> ok(String message, T data) {
		return new ApiResponse<>(HttpStatus.OK.value(), true, message, data);
	}

	/**
	 * 201 Created - 생성 성공
	 */
	public static <T> ApiResponse<T> created(T data) {
		return new ApiResponse<>(HttpStatus.CREATED.value(), true, "리소스가 생성되었습니다.", data);
	}

	public static <T> ApiResponse<T> created(String message, T data) {
		return new ApiResponse<>(HttpStatus.CREATED.value(), true, message, data);
	}

	// ==================== Legacy Support ====================

	/**
	 * @deprecated Use ok() or created() instead
	 */
	@Deprecated
	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(HttpStatus.OK.value(), true, message, data);
	}

	/**
	 * @deprecated Use ok() instead
	 */
	@Deprecated
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(HttpStatus.OK.value(), true, "success", data);
	}

	// ==================== Error Responses ====================

	/**
	 * 400 Bad Request
	 */
	public static <T> ApiResponse<T> badRequest(String message) {
		return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), false, message, null);
	}

	/**
	 * 404 Not Found
	 */
	public static <T> ApiResponse<T> notFound(String message) {
		return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), false, message, null);
	}

	/**
	 * 500 Internal Server Error
	 */
	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, null);
	}

	/**
	 * Custom error with status code
	 */
	public static <T> ApiResponse<T> fail(HttpStatus status, String message) {
		return new ApiResponse<>(status.value(), false, message, null);
	}

	/**
	 * @deprecated Use fail(HttpStatus, String) instead
	 */
	@Deprecated
	public static <T> ApiResponse<T> fail(String message, T errorData) {
		return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, errorData);
	}
}