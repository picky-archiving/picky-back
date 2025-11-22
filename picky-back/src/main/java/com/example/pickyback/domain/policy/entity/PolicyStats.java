package com.example.pickyback.domain.policy.entity;

import com.example.pickyback.global.entity.BaseEntity;
import jakarta.persistence.*;
	import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "policy_stats")
public class PolicyStats extends BaseEntity {

	// PK 이면서 FK
	@Id
	@Column(name = "policy_id")
	private Long policyId;

	@MapsId // PK를 Policy의 id와 공유
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id")
	private Policy policy;

	@Column(name = "view_count", nullable = false)
	private Long viewCount;

	public static PolicyStats init(Policy policy) {
		PolicyStats stats = new PolicyStats();
		stats.policy = policy;
		stats.viewCount = 0L;
		return stats;
	}

	public void increaseViewCount() {
		this.viewCount++;
	}
}
