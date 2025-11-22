package com.example.pickyback.domain.user.entity;


import com.example.pickyback.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	// 소득 구간
	@Column(name = "income_bracket", nullable = false)
	private Long incomeBracket;

	public void updateIncomeBracket(Long incomeBracket) {
		this.incomeBracket = incomeBracket;
	}

	public static User create(Long incomeBracket) {
		User user = new User();
		user.incomeBracket = incomeBracket;
		return user;
	}
}
