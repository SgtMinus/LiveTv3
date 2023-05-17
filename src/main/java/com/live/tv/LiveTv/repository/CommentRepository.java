package com.live.tv.LiveTv.repository;

import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByBroadcastId(Long broadcastId, Pageable pageable);

    Page<Comment> findAllByBroadcastIdAndCommentStatus(Long broadcastId, CommentStatus commentStatus, Pageable pageable);

    List<Comment> findAllByCommentStatus(CommentStatus commentStatus);

    void deleteByIdAndBroadcastIdAndAuthorId(Long id, Long broadcastId, Long authorId);

    boolean existsByIdAndBroadcastIdAndAuthorId(Long id, Long broadcastId, Long authorId);

    boolean existsByIdAndBroadcastId(Long id, Long broadcastId);

    Comment findByIdAndBroadcastIdAndAuthorId(Long id, Long broadcastId, Long authorId);
}
