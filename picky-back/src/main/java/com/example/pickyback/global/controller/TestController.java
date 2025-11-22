package com.example.pickyback.global.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pickyback.global.dto.ApiResponse;


@Tag(name = "System")
@RestController
public class TestController {

    /**
     * Health check - 루트 경로
     * GET /
     * @return 200 OK
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> home() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("서버가 정상 동작 중입니다.", "Hello World"));
    }

    /**
     * 테스트 엔드포인트
     * GET /test
     * @return 200 OK
     */
    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("테스트 성공", "test"));
    }
}