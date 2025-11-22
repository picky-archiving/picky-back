package com.example.pickyback.repository.policy;

import com.example.pickyback.domain.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinglePolicyRepository extends JpaRepository<Policy, Long> {
}
