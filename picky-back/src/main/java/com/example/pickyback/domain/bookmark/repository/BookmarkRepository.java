package com.example.pickyback.domain.bookmark.repository;

import com.example.pickyback.domain.bookmark.entity.Bookmark;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // 마이페이지 북마크 목록
    Page<Bookmark> findByUserId(Long userId, Pageable pageable);
    boolean existsByPolicyIdAndActive(Long policyId, boolean active);

    // 북마크 중복 방지
    boolean existsByUserIdAndPolicyIdAndActiveTrue(Long userId, Long policyId);

    // 북마크 삭제용
    Optional<Bookmark> findByUserIdAndPolicyId(Long userId, Long policyId);
}
