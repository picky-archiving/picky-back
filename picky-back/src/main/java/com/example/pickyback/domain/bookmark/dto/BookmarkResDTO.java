package com.example.pickyback.domain.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkResDTO {

    private Long policyId;
    private boolean bookmarked;     // 항상 true
}
