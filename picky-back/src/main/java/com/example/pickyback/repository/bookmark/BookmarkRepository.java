package com.example.pickyback.repository.bookmark;

import com.example.pickyback.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByPolicyIdAndActive(Long policyId, boolean active);
}
