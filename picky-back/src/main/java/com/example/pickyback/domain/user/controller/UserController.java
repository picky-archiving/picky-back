package com.example.pickyback.domain.user.controller;

import com.example.pickyback.domain.user.dto.UserRequestDto;
import com.example.pickyback.domain.user.dto.UserResponseDto;
import com.example.pickyback.domain.user.service.UserService;
import com.example.pickyback.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable(required = false) Long userId) {
        // userId 없으면 기본값 1
        Long targetUserId = (userId != null) ? userId : 1L;

        UserResponseDto user = userService.getUser(targetUserId);
        return ApiResponse.success(user);
    }

    @PostMapping("/{userId}")
    public ApiResponse<UserResponseDto> updateUser(
            @PathVariable(required = false) Long userId,
            @RequestBody UserRequestDto request
    ) {
        // userId 없으면 기본값 1
        Long targetUserId = (userId != null) ? userId : 1L;

        UserResponseDto updatedUser = userService.updateUser(targetUserId, request);
        return ApiResponse.success(updatedUser);
    }
}
