package com.example.pickyback.repository.policy;

import com.example.pickyback.domain.policy.entity.PolicyStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyStatsRepository extends JpaRepository<PolicyStats, Long> {
}
