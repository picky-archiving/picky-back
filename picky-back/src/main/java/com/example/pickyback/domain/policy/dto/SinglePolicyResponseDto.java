package com.example.pickyback.domain.policy.dto;

import com.example.pickyback.domain.policy.entity.Policy;
import com.example.pickyback.domain.policy.entity.PolicyStats;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class SinglePolicyResponseDto {

    private Long id;
    private String category;
    private String title;
    private String content;
    private String url;
    private Long limitIncomeBracket;
    private String imageUrl;
    private String host;
    private List<String> qualifications;
    private boolean always;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long viewCount;
    private boolean bookmarked;

    public static SinglePolicyResponseDto from(Policy policy, PolicyStats stats, boolean bookmarked) {
        return SinglePolicyResponseDto.builder()
                .id(policy.getId())
                .category(policy.getCategory())
                .title(policy.getTitle())
                .content(policy.getContent())
                .url(policy.getUrl())
                .limitIncomeBracket(policy.getLimitIncomeBracket())
                .imageUrl(policy.getImageUrl())
                .host(policy.getHost())
                .qualifications(parseQualifications(policy.getQualifications()))
                .always(policy.isAlways())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .viewCount(stats.getViewCount())
                .bookmarked(bookmarked)
                .build();
    }

    private static List<String> parseQualifications(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
