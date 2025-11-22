package com.example.pickyback.domain.user.service;

import static com.example.pickyback.global.exception.CommonErrorCode.USER_NOT_FOUND;

import com.example.pickyback.domain.user.dto.UserRequestDto;
import com.example.pickyback.domain.user.dto.UserResponseDto;
import com.example.pickyback.domain.user.entity.User;
import com.example.pickyback.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long userId) {
        return UserResponseDto.from(userRepository.findById(userId).orElseThrow());
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USER_NOT_FOUND.getMessage()));
        user.updateIncomeBracket(request.getIncomeBracket());
        return UserResponseDto.from(user);
    }
}
