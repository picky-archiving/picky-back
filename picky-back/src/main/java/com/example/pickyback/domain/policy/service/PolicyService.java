package com.example.pickyback.domain.policy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.pickyback.domain.policy.dto.HomeResponseDto;
import com.example.pickyback.domain.policy.dto.PolicyResponseDto;

public interface PolicyService {

	Page<PolicyResponseDto> getAllPolicies(Pageable pageable);

	List<PolicyResponseDto> getTop3PoliciesByCategory(String category);

	List<PolicyResponseDto> getTop3PoliciesByIncomeLevel(Long userId);

	HomeResponseDto getHome(Long userId);

	// 카테고리 상세 화면용 - 최신/인기 정렬
	Page<PolicyResponseDto> getPoliciesByCategoryWithSort(
		String category,
		String sort,
		Pageable pageable
	);

}
