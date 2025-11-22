package com.example.pickyback.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // ==================== Success (2xx) ====================
    SUCCESS(HttpStatus.OK, "요청이 성공했습니다."),
    CREATED(HttpStatus.CREATED, "리소스가 생성되었습니다."),

    // ==================== Client Error (4xx) ====================
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청이 올바르지 않습니다. 입력 값을 다시 확인해주세요."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    // Domain specific 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    POLICY_NOT_FOUND(HttpStatus.NOT_FOUND, "정책이 존재하지 않습니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "북마크를 찾을 수 없습니다."),

    // ==================== Server Error (5xx) ====================
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 상태가 원활하지 않습니다. 잠시 후 다시 시도해주세요.");

    private final HttpStatus status;
    private final String message;
}