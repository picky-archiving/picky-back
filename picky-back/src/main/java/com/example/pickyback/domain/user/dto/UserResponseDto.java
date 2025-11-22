package com.example.pickyback.domain.user.dto;

import com.example.pickyback.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private Long incomeBracket;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .incomeBracket(user.getIncomeBracket())
                .build();
    }
}
