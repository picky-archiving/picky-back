package com.example.pickyback.domain.policy.controller;

import com.example.pickyback.domain.bookmark.dto.BookmarkResDTO;
import com.example.pickyback.domain.bookmark.service.BookmarkService;
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
    private final BookmarkService bookmarkService;

    // 북마크 여부에 따른 게시물 조회
    @GetMapping("/bookmarked")
    public ApiResponse<PageResponse<BookmarkedPolicyResDTO>> getbookmarkedPolicies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            ) {
        Long userId = 1L;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<BookmarkedPolicyResDTO> result =
                policyQueryService.getBookmarkedPolicies(userId, pageable);
        return ApiResponse.success("북마크된 정책 조회에 성공했습니다. ", new PageResponse<>(result));
    }

    // 게시물 북마크 표시 변경
    @PostMapping("/{policyId}/bookmark")
    public ApiResponse<BookmarkResDTO> addBookmark(
            @PathVariable Long policyId
    ){
        BookmarkResDTO result = bookmarkService.addBookmark(policyId);
        return ApiResponse.success("북마크가 등록되었습니다.", result);
    }

    @DeleteMapping("/{policyId}/bookmark")
    public ApiResponse<BookmarkResDTO> removeBookmark(
            @PathVariable Long policyId
    ){
        BookmarkResDTO result = bookmarkService.removeBookmark(policyId);
        return ApiResponse.success("북마크가 해제되었습니다.", result);
    }
}
