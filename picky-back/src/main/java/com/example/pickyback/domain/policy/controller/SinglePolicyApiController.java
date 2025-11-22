package com.example.pickyback.domain.policy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pickyback.domain.policy.dto.SinglePolicyResponseDto;
import com.example.pickyback.domain.policy.service.SinglePolicyService;
import com.example.pickyback.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class SinglePolicyApiController {

    private final SinglePolicyService singlePolicyService;

    /**
     * 단일 정책 상세 조회 (조회수 증가 포함)
     * GET /api/post/{policyId}
     * @return 200 OK
     */
    @GetMapping("/{policyId}")
    public ResponseEntity<ApiResponse<SinglePolicyResponseDto>>
    getSinglePolicy(
            @PathVariable String policyId) {
        SinglePolicyResponseDto response =
                singlePolicyService.increaseViewAndGetPolicy(
                        Long.parseLong(policyId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("정책 상세 조회 성공", response));
    }
}