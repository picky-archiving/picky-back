package com.example.pickyback.domain.policy.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.pickyback.domain.policy.dto.HomeResponseDto;
import com.example.pickyback.domain.policy.dto.PolicyResponseDto;
import com.example.pickyback.domain.policy.service.PolicyService;
import com.example.pickyback.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import com.example.pickyback.domain.bookmark.dto.BookmarkResDTO;
import com.example.pickyback.domain.bookmark.service.BookmarkService;
import com.example.pickyback.domain.policy.dto.BookmarkedPolicyResDTO;
import com.example.pickyback.domain.policy.service.PolicyQueryService;
import com.example.pickyback.global.dto.PageResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;
    private final PolicyQueryService policyQueryService;
    private final BookmarkService bookmarkService;

    // 북마크 여부에 따른 게시물 조회
    @GetMapping("/post/bookmarked")
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
    @PostMapping("/post/{policyId}/bookmark")
    public ApiResponse<BookmarkResDTO> addBookmark(
            @PathVariable Long policyId
    ){
        BookmarkResDTO result = bookmarkService.addBookmark(policyId);
        return ApiResponse.success("북마크가 등록되었습니다.", result);
    }

    @DeleteMapping("/post/{policyId}/bookmark")
    public ApiResponse<BookmarkResDTO> removeBookmark(
            @PathVariable Long policyId
    ) {
        BookmarkResDTO result = bookmarkService.removeBookmark(policyId);
        return ApiResponse.success("북마크가 해제되었습니다.", result);

    }


        /** 전체 게시물 목록 조회 (페이징)*/
        @GetMapping("/post")
        public ResponseEntity<ApiResponse<Page<PolicyResponseDto>>> getAllPolicy (
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
	){
            Pageable pageable = PageRequest.of(page, size);

            Page<PolicyResponseDto> policies = policyService.getAllPolicies(pageable);

            return ResponseEntity.ok(
                ApiResponse.success("전체 게시물 목록 조회 성공", policies)
            );
        }

        /**  Home 화면*/
        @GetMapping("/home")
        public ResponseEntity<ApiResponse<HomeResponseDto>> getHome (
            @RequestHeader(value = "X-USER-ID", required = false, defaultValue = "1") Long userId
	){
            HomeResponseDto home = policyService.getHome(userId);

            return ResponseEntity.ok(
                ApiResponse.success("홈 데이터 조회 성공", home)
            );
        }

        /**  카테고리별 조회 (상위 3개만)*/
        @GetMapping("/home/category/{category}")
        public ResponseEntity<ApiResponse<List<PolicyResponseDto>>> getPolicyByCategory (
            @PathVariable String category
	){
            List<PolicyResponseDto> policies = policyService.getTop3PoliciesByCategory(category);

            return ResponseEntity.ok(
                ApiResponse.success("카테고리별 게시물 조회 성공", policies)
            );
        }

        /** 소득분위별 조회 (상위 3개만)
         X-USER-ID 헤더 없으면 userId = 1로 고정 */
        @GetMapping("/post/income")
        public ResponseEntity<ApiResponse<List<PolicyResponseDto>>> getPolicyByIncome (
            @RequestHeader(value = "X-USER-ID", required = false, defaultValue = "1") Long userId
	){
            List<PolicyResponseDto> policies = policyService.getTop3PoliciesByIncomeLevel(userId);

            return ResponseEntity.ok(
                ApiResponse.success("소득분위별 게시물 조회 성공", policies)
            );
        }

        /**
         *  카테고리 상세 전체 조회
         * - 최신순: sort=latest (기본값)
         * - 인기순: sort=popular
         * 예시: GET /api/category/취업?sort=popular&page=0&size=10
         */
        @GetMapping("/category/{category}")
        public ResponseEntity<ApiResponse<Page<PolicyResponseDto>>> getCategoryPosts (
            @PathVariable String category,
            @RequestParam(defaultValue = "latest") String sort,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
	){
            Pageable pageable = PageRequest.of(page, size);

            Page<PolicyResponseDto> policies =
                policyService.getPoliciesByCategoryWithSort(category, sort, pageable);

            return ResponseEntity.ok(
                ApiResponse.success("카테고리별 전체 게시물 조회 성공", policies)
            );
        }

    }
