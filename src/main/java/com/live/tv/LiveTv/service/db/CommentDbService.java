package com.live.tv.LiveTv.service.db;

import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.CommentStatus;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentDbService {
    private final String ENTITY_ROLE_CLASS_NAME = "Комментарий";

    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> saveAll(List<Comment> comments) {
        return commentRepository.saveAll(comments);
    }

    @Transactional
    public void deleteByIdAndBroadcastIdAndAuthorId(Long commentId, Long broadcastId, Long authorId) {
        commentRepository.deleteByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, authorId);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAllByBroadcastId(Long broadcastId, Pageable pageable) {
        return commentRepository.findAllByBroadcastId(broadcastId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAllByBroadcastIdAndCommentStatus(Long broadcastId, CommentStatus commentStatus, Pageable pageable) {
        return commentRepository.findAllByBroadcastIdAndCommentStatus(broadcastId, commentStatus, pageable);
    }

    @Transactional(readOnly = true)
    public boolean existsByIdAndBroadcastIdAndAuthorId(Long commentId, Long broadcastId, Long authorId) {
        return !commentRepository.existsByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, authorId);
    }

    @Transactional(readOnly = true)
    public boolean existsByIdAndBroadcastId(Long commentId, Long broadcastId) {
        return !commentRepository.existsByIdAndBroadcastId(commentId, broadcastId);
    }

    @Transactional(readOnly = true)
    public Comment findByIdAndBroadcastIdAndAuthorId(Long commentId, Long broadcastId, Long authorId) {
        return commentRepository.findByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, authorId);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllByCommentStatus(CommentStatus commentStatus) {
        return commentRepository.findAllByCommentStatus(commentStatus);
    }
}
