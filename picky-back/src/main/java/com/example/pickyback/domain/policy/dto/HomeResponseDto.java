package com.example.pickyback.domain.policy.dto;


import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeResponseDto {

	// 맨 위 소득분위별 추천
	private List<PolicyResponseDto> incomePolicies;

	// 아래 카테고리 섹션들 (취업, 주거, 금융, 교육 ...)
	private List<CategorySectionDto> categories;

	@Getter
	@Builder
	public static class CategorySectionDto {
		private String category;
		private List<PolicyResponseDto> policies;     // 각 카테고리의 top3
	}
}

