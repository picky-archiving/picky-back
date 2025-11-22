package com.example.pickyback.domain.bookmark.repository;

import com.example.pickyback.domain.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Page<Bookmark> findByUserId(Long userId, Pageable pageable);
}
