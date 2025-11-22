package com.example.pickyback.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 서버 상태 (500)
    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "서버 상태가 원활하지 않습니다. 잠시 후 다시 시도해주세요."
    ),

    // 클라이언트가 요청 잘못했을 때 (400)
    BAD_REQUEST(
            HttpStatus.BAD_REQUEST,
            "요청이 올바르지 않습니다. 입력 값을 다시 확인해주세요."
    ),

    // 404 계열
    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "사용자를 찾을 수 없습니다."
    ),
    POLICY_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "정책이 존재하지 않습니다."
    );   

    private final HttpStatus status;
    private final String message;
}
