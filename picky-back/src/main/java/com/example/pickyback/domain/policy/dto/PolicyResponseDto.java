package com.example.pickyback.domain.policy.dto;

import java.time.LocalDate;

import com.example.pickyback.domain.policy.entity.Policy;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyResponseDto {

	private Long id;
	private String category;      // VARCHAR 그대로 사용
	private String title;
	private String host;
	private String imageUrl;
	private String url;
	private String qualifications;
	private boolean always;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long viewCount;       // PolicyStats 에서 가져온 값

	/**
	 * 조회수 정보 없이 Policy -> DTO 변환할 때 사용
	 */
	public static PolicyResponseDto from(Policy policy) {
		return from(policy, null);
	}

	/**
	 * 조회수(viewCount)를 따로 받아서 넣고 싶을 때 사용
	 */
	public static PolicyResponseDto from(Policy policy, Long viewCount) {
		return PolicyResponseDto.builder()
			.id(policy.getId())
			.category(policy.getCategory())
			.title(policy.getTitle())
			.host(policy.getHost())
			.imageUrl(policy.getImageUrl())
			.url(policy.getUrl())
			.qualifications(policy.getQualifications())
			.always(policy.isAlways())
			.startDate(policy.getStartDate())
			.endDate(policy.getEndDate())
			.viewCount(viewCount)
			.build();
	}
}
