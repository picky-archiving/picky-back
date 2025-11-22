package com.example.pickyback.domain.policy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pickyback.domain.policy.dto.HomeResponseDto;
import com.example.pickyback.domain.policy.dto.PolicyResponseDto;
import com.example.pickyback.domain.policy.entity.Policy;
import com.example.pickyback.domain.policy.entity.PolicyStats;
import com.example.pickyback.domain.policy.repository.PolicyRepository;
import com.example.pickyback.domain.policy.repository.PolicyStatsRepository;
import com.example.pickyback.domain.user.entity.User;
import com.example.pickyback.domain.user.repository.UserRepository;
import com.example.pickyback.global.exception.BaseException;
import com.example.pickyback.global.exception.CommonErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyServiceImpl implements PolicyService {

	private final PolicyRepository policyRepository;
	private final PolicyStatsRepository policyStatsRepository;
	private final UserRepository userRepository;

	/** 0. 전체 정책 목록 */
	@Override
	public Page<PolicyResponseDto> getAllPolicies(Pageable pageable) {
		Page<Policy> page = policyRepository.findAll(pageable);
		return page.map(PolicyResponseDto::from);
	}

	/** 1. 홈 화면 */
	@Override
	public HomeResponseDto getHome(Long userId) {
		// 1) 소득분위별 추천
		List<PolicyResponseDto> incomePolicies = getTop3PoliciesByIncomeLevel(userId);

		// 2) 카테고리 섹션들 (필요한 카테고리만 하드코딩)
		List<HomeResponseDto.CategorySectionDto> categories = List.of(
			buildCategorySection("finance"),
			buildCategorySection("employment"),
			buildCategorySection("education"),
			buildCategorySection("housing")
		);

		return HomeResponseDto.builder()
			.incomePolicies(incomePolicies)
			.categories(categories)
			.build();
	}

	private HomeResponseDto.CategorySectionDto buildCategorySection(String category) {
		List<PolicyResponseDto> policies = getTop3PoliciesByCategory(category);
		return HomeResponseDto.CategorySectionDto.builder()
			.category(category)
			.policies(policies)
			.build();
	}

	/** 2. 카테고리별 상위 3개 (홈에서 사용) */
	@Override
	public List<PolicyResponseDto> getTop3PoliciesByCategory(String category) {
		List<Policy> policies =
			policyRepository.findTop3ByCategoryOrderByCreatedAtDesc(category);

		return policies.stream()
			.map(PolicyResponseDto::from)
			.toList();
	}

	/** 3. 소득분위별 추천 상위 3개 (userId 기반) */
	@Override
	public List<PolicyResponseDto> getTop3PoliciesByIncomeLevel(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BaseException(CommonErrorCode.BAD_REQUEST));

		Long incomeBracket = user.getIncomeBracket();

		List<Policy> policies =
			policyRepository.findTop3ByLimitIncomeBracketIsNullOrLimitIncomeBracketGreaterThanEqualOrderByCreatedAtDesc(
				incomeBracket
			);

		return policies.stream()
			.map(PolicyResponseDto::from)
			.toList();
	}

	/** 4. 카테고리 상세 – 최신 / 인기 정렬 */
	@Override
	public Page<PolicyResponseDto> getPoliciesByCategoryWithSort(
		String category,
		String sort,
		Pageable pageable
	) {
		String key = (sort == null) ? "latest" : sort.toLowerCase();

		// 인기순
		if ("popular".equals(key)) {
			Page<PolicyStats> page =
				policyStatsRepository.findByPolicyCategoryOrderByViewCountDesc(category, pageable);

			return page.map(ps -> PolicyResponseDto.from(ps.getPolicy(), ps.getViewCount()));
		}

		// 기본: 최신순
		Page<Policy> page =
			policyRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);

		return page.map(PolicyResponseDto::from);
	}
}
