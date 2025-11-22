package com.example.pickyback.domain.policy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pickyback.domain.policy.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

	// 카테고리별 상위 3개
	List<Policy> findTop3ByCategoryOrderByCreatedAtDesc(String category);

	// 소득분위별 상위 3개
	List<Policy> findTop3ByLimitIncomeBracketIsNullOrLimitIncomeBracketGreaterThanEqualOrderByCreatedAtDesc(
		Long incomeBracket
	);

	//카테고리 전체 조회 - 최신순 + 페이징
	Page<Policy> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);
}
