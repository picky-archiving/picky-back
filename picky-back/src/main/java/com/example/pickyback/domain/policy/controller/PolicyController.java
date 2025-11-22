package com.example.pickyback.domain.policy.controller;

import com.example.pickyback.domain.policy.dto.BookmarkedPolicyResDTO;
import com.example.pickyback.domain.policy.service.PolicyQueryService;
import com.example.pickyback.global.dto.ApiResponse;
import com.example.pickyback.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyQueryService policyQueryService;

    @GetMapping("/bookmarked")
    public ApiResponse<PageResponse<BookmarkedPolicyResDTO>> getbookmarkedPolicies(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            ) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<BookmarkedPolicyResDTO> result =
                policyQueryService.getBookmarkedPolicies(userId, pageable);
        return ApiResponse.success("북마크된 정책 조회에 성공했습니다. ", new PageResponse<>(result));
    }


}
