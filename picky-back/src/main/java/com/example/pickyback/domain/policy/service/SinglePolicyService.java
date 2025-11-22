package com.example.pickyback.domain.policy.service;

import static com.example.pickyback.global.exception.CommonErrorCode.POLICY_NOT_FOUNT_ERROR;

import com.example.pickyback.domain.bookmark.repository.BookmarkRepository;
import com.example.pickyback.domain.policy.entity.Policy;
import com.example.pickyback.domain.policy.entity.PolicyStats;
import com.example.pickyback.domain.policy.repository.PolicyStatsRepository;
import com.example.pickyback.domain.policy.dto.SinglePolicyResponseDto;
import com.example.pickyback.domain.policy.repository.SinglePolicyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SinglePolicyService {

    private final SinglePolicyRepository singlePolicyRepository;
    private final PolicyStatsRepository policyStatsRepository;
    private final BookmarkRepository bookmarkRepository;

    public SinglePolicyResponseDto increaseViewAndGetPolicy(Long policyId) {
        Policy policy = singlePolicyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException(POLICY_NOT_FOUNT_ERROR.getMessage()));

        PolicyStats policyStats = increaseView(policy);
        boolean bookmarked = bookmarkRepository.existsByPolicyIdAndActive(policyId, true);
        return SinglePolicyResponseDto.from(policy, policyStats, bookmarked);
    }

    private PolicyStats increaseView(Policy policy) {
        //통계가 존재하면 업데이트, 아니면 새로 생성
        PolicyStats stats = policyStatsRepository.findById(policy.getId())
                .orElseGet(() -> {
                    PolicyStats newStats = PolicyStats.init(policy);
                    return policyStatsRepository.save(newStats);
                });

        // 조회수 증가
        stats.increaseViewCount();

        return stats;
    }
}
