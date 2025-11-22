package com.example.pickyback.domain.policy.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookmarkedPolicyResDTO {
    private Long id;
    private String category;
    private String title;
    private String host;
    private String imageUrl;

    private boolean always;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private Long dDay;

    private Long viewCount;
    private boolean bookmarked;     // 항상 true
}
