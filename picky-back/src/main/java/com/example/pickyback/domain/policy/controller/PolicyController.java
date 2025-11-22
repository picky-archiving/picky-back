package com.example.pickyback.domain.policy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pickyback.domain.bookmark.dto.BookmarkResDTO;
import com.example.pickyback.domain.bookmark.service.BookmarkService;
import com.example.pickyback.domain.policy.dto.BookmarkedPolicyResDTO;
import com.example.pickyback.domain.policy.dto.HomeResponseDto;
import com.example.pickyback.domain.policy.dto.PolicyResponseDto;
import com.example.pickyback.domain.policy.service.PolicyQueryService;
import com.example.pickyback.domain.policy.service.PolicyService;
import com.example.pickyback.global.dto.ApiResponse;
import com.example.pickyback.global.dto.PageResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;
    private final PolicyQueryService policyQueryService;
    private final BookmarkService bookmarkService;

    /**
     * 북마크된 정책 목록 조회 (페이징)
     * GET /api/post/bookmarked
     * @return 200 OK
     */
    @Tag(name = "Policy")
    @GetMapping("/post/bookmarked")
    public ResponseEntity<ApiResponse<PageResponse<BookmarkedPolicyResDTO>>>
    getBookmarkedPolicies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC,
                "id");

        Page<BookmarkedPolicyResDTO> result =
                policyQueryService.getBookmarkedPolicies(userId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("북마크된 정책 조회 성공", new
                        PageResponse<>(result)));
    }

    /**
     * 북마크 등록
     * POST /api/post/{policyId}/bookmark
     * @return 201 Created
     */
    @Tag(name = "Bookmark")
    @PostMapping("/post/{policyId}/bookmark")
    public ResponseEntity<ApiResponse<BookmarkResDTO>> addBookmark(
            @PathVariable Long policyId) {
        BookmarkResDTO result = bookmarkService.addBookmark(policyId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("북마크가 등록되었습니다.", result));
    }

    /**
     * 북마크 해제
     * DELETE /api/post/{policyId}/bookmark
     * @return 200 OK
     */
    @Tag(name = "Bookmark")
    @DeleteMapping("/post/{policyId}/bookmark")
    public ResponseEntity<ApiResponse<BookmarkResDTO>> removeBookmark(
            @PathVariable Long policyId) {
        BookmarkResDTO result = bookmarkService.removeBookmark(policyId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("북마크가 해제되었습니다.", result));
    }

    /**
     * 전체 게시물 목록 조회 (페이징)
     * GET /api/post
     * @return 200 OK
     */
    @Tag(name = "Policy")
    @GetMapping("/post")
    public ResponseEntity<ApiResponse<Page<PolicyResponseDto>>> getAllPolicy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PolicyResponseDto> policies =
                policyService.getAllPolicies(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("전체 게시물 목록 조회 성공", policies));
    }

    /**
     * Home 화면 데이터 조회
     * GET /api/home
     * @return 200 OK
     */

    @Tag(name = "Home")
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<HomeResponseDto>> getHome(
            @RequestHeader(value = "X-USER-ID", required = false, defaultValue
                    = "1") Long userId) {
        HomeResponseDto home = policyService.getHome(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("홈 데이터 조회 성공", home));
    }

    /**
     * 카테고리별 정책 조회 (상위 3개)
     * GET /api/home/category/{category}
     * @return 200 OK
     */
    @Tag(name = "Policy")
    @GetMapping("/home/category/{category}")
    public ResponseEntity<ApiResponse<List<PolicyResponseDto>>>
    getPolicyByCategory(
            @PathVariable String category) {
        List<PolicyResponseDto> policies =
                policyService.getTop3PoliciesByCategory(category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("카테고리별 게시물 조회 성공", policies));
    }

    /**
     * 소득분위별 정책 조회 (상위 3개)
     * GET /api/post/income
     * @return 200 OK
     */
    @Tag(name = "Policy")
    @GetMapping("/post/income")
    public ResponseEntity<ApiResponse<List<PolicyResponseDto>>>
    getPolicyByIncome(
            @RequestHeader(value = "X-USER-ID", required = false, defaultValue
                    = "1") Long userId) {
        List<PolicyResponseDto> policies =
                policyService.getTop3PoliciesByIncomeLevel(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("소득분위별 게시물 조회 성공", policies));
    }

    /**
     * 카테고리별 전체 정책 조회 (페이징 + 정렬)
     * GET /api/category/{category}
     * @param sort latest(최신순) | popular(인기순)
     * @return 200 OK
     */
    @Tag(name = "Policy")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<PolicyResponseDto>>>
    getCategoryPosts(
            @PathVariable String category,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PolicyResponseDto> policies =
                policyService.getPoliciesByCategoryWithSort(category, sort, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok("카테고리별 전체 게시물 조회 성공",
                        policies));
    }
}