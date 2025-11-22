package com.example.pickyback.domain.bookmark.service;

import com.example.pickyback.domain.bookmark.dto.BookmarkResDTO;
import com.example.pickyback.domain.bookmark.entity.Bookmark;
import com.example.pickyback.domain.bookmark.repository.BookmarkRepository;
import com.example.pickyback.domain.policy.entity.Policy;
import com.example.pickyback.domain.policy.repository.PolicyRepository;
import com.example.pickyback.domain.user.entity.User;
import com.example.pickyback.domain.user.repository.UserRepository;
import com.example.pickyback.global.exception.BaseException;
import com.example.pickyback.global.exception.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PolicyRepository policyRepository;
    private final UserRepository userRepository;

    private static final Long DEMO_USER_ID = 1L;

    // 북마크 등록
    @Transactional
    public BookmarkResDTO addBookmark(Long policyId) {

        Long userId = DEMO_USER_ID;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(CommonErrorCode.USER_NOT_FOUND));

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new BaseException(CommonErrorCode.POLICY_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByUserIdAndPolicyId(userId, policyId).orElse(null);

        if (bookmark == null) {
            bookmark = Bookmark.builder()
                    .user(user)
                    .policy(policy)
                    .active(true)
                    .build();

        }else{
            bookmark.activate();    // row가 이미 있는데, active = false 였으면 되살리기
        }

        bookmarkRepository.save(bookmark);

        return BookmarkResDTO.builder()
                .policyId(policyId)
                .bookmarked(true)
                .build();
    }

    // 북마크 해제
    @Transactional
    public BookmarkResDTO removeBookmark(Long policyId) {

        Long userId = DEMO_USER_ID;

        if(!userRepository.existsById(userId)){
            throw new BaseException(CommonErrorCode.USER_NOT_FOUND);
        }

        if (!policyRepository.existsById(policyId)) {
            throw new BaseException(CommonErrorCode.POLICY_NOT_FOUND);
        }

        Bookmark bookmark = bookmarkRepository.findByUserIdAndPolicyId(userId, policyId).orElse(null);

        if(bookmark!=null && bookmark.isActive()){
            bookmark.deactivate();
            bookmarkRepository.save(bookmark);
        }

        return  BookmarkResDTO.builder()
                .policyId(policyId)
                .bookmarked(false)
                .build();
    }
}
