package com.example.pickyback.controller.policy;

import com.example.pickyback.dto.policy.SinglePolicyResponseDto;
import com.example.pickyback.global.dto.ApiResponse;
import com.example.pickyback.service.policy.SinglePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class SinglePolicyApiController {

    private final SinglePolicyService singlePolicyService;

    @GetMapping("/{policyId}")
    public ApiResponse<SinglePolicyResponseDto> getSinglePolicy(@PathVariable String policyId) {

        SinglePolicyResponseDto singlePolicyResponseDto = singlePolicyService.increaseViewAndGetPolicy(
                Long.parseLong(policyId));

        return ApiResponse.success(singlePolicyResponseDto);
    }
}
