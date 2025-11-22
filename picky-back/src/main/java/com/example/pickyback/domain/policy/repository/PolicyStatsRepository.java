package com.example.pickyback.domain.policy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pickyback.domain.policy.entity.PolicyStats;

public interface PolicyStatsRepository extends JpaRepository<PolicyStats, Long> {

	// 카테고리 필터 + 조회수 내림차순 + 페이징
	@Query("""
        select ps
        from PolicyStats ps
            join ps.policy p
        where p.category = :category
        order by ps.viewCount desc
        """)
	Page<PolicyStats> findByPolicyCategoryOrderByViewCountDesc(
		@Param("category") String category,
		Pageable pageable
	);
}
