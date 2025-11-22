package com.example.pickyback.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pickyback.domain.user.dto.UserRequestDto;
import com.example.pickyback.domain.user.dto.UserResponseDto;
import com.example.pickyback.domain.user.service.UserService;
import com.example.pickyback.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 조회
     * GET /api/user/{userId}
     * @return 200 OK
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(
            @PathVariable(required = false) Long userId) {
        Long targetUserId = (userId != null) ? userId : 1L;

        UserResponseDto user = userService.getUser(targetUserId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("사용자 조회 성공", user));
    }

    /**
     * 사용자 정보 수정
     * POST /api/user/{userId}
     * @return 200 OK
     */
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @PathVariable(required = false) Long userId,
            @RequestBody UserRequestDto request) {
        Long targetUserId = (userId != null) ? userId : 1L;

        UserResponseDto updatedUser = userService.updateUser(targetUserId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("사용자 정보 수정 성공", updatedUser));
    }
}