package com.live.tv.LiveTv.repository;

import com.live.tv.LiveTv.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    void deleteAllByCommentId(Long commentId);

    Optional<Interest> findByIdAndUserId(Long interestId, Long userId);

    Optional<Interest> findByCommentIdAndUserId(Long commentId, Long userId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}
