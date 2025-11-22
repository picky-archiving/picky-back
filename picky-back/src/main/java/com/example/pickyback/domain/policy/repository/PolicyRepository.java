package com.example.pickyback.domain.policy.repository;

import com.example.pickyback.domain.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
