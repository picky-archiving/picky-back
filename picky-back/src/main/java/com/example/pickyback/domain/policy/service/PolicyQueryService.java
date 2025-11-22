package com.example.pickyback.domain.policy.service;

import com.example.pickyback.domain.bookmark.entity.Bookmark;
import com.example.pickyback.domain.bookmark.repository.BookmarkRepository;
import com.example.pickyback.domain.policy.dto.BookmarkedPolicyResDTO;
import com.example.pickyback.domain.policy.entity.Policy;
import com.example.pickyback.domain.policy.entity.PolicyStats;
import com.example.pickyback.domain.policy.repository.PolicyStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PolicyQueryService {

    private final BookmarkRepository bookmarkRepository;
    private final PolicyStatsRepository policyStatsRepository;

    // 유저가 북마크한 정책 목록 조회
    public Page<BookmarkedPolicyResDTO> getBookmarkedPolicies(Long userId, Pageable pageable) {
        Page<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId, pageable);

        return bookmarks.map(this::toBookmarkedPolicyResDTO);
    }

    private BookmarkedPolicyResDTO toBookmarkedPolicyResDTO(Bookmark bookmark) {
        Policy policy = bookmark.getPolicy();

        PolicyStats stats = policyStatsRepository.findById(policy.getId())
                .orElse(null);

        long viewCount = (stats != null) ? stats.getViewCount() : 0L;

        Long dDay = null;
        if(!policy.isAlways() && policy.getEndDate() != null){
            long days = ChronoUnit.DAYS.between(LocalDate.now(), policy.getEndDate());
            dDay = days;
        }

        return BookmarkedPolicyResDTO.builder()
                .id(policy.getId())
                .category(policy.getCategory())
                .title(policy.getTitle())
                .host(policy.getHost())
                .always(policy.isAlways())
                .StartDate(policy.getStartDate())
                .EndDate(policy.getEndDate())
                .dDay(dDay)
                .viewCount(viewCount)
                .bookmarked(true)
                .build();
    }
}
