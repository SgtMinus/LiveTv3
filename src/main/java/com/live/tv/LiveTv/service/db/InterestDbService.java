package com.live.tv.LiveTv.service.db;

import com.live.tv.LiveTv.entity.Interest;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.repository.InterestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InterestDbService {
    private final String ENTITY_ROLE_CLASS_NAME = "Интерес";
    private final InterestRepository interestRepository;


    @Transactional
    public Interest save(Interest interest) {
        return interestRepository.save(interest);
    }

    @Transactional
    public void delete(Interest interest) {
        interestRepository.delete(interest);
    }

    @Transactional(readOnly = true)
    public Interest findByIdAndUserId(Long interestId, Long userId) {
        return interestRepository.findByIdAndUserId(interestId, userId).orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));
    }

    @Transactional(readOnly = true)
    public Interest findByCommentIdAndUserId(Long commentId, Long userId) {
        return interestRepository.findByCommentIdAndUserId(commentId, userId).orElseThrow(() -> new EntityNotFoundException(ENTITY_ROLE_CLASS_NAME));
    }

    @Transactional(readOnly = true)
    public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
        return interestRepository.existsByCommentIdAndUserId(commentId, userId);
    }

    @Transactional
    public void deleteAllByCommentId(Long commentId) {
        interestRepository.deleteAllByCommentId(commentId);
    }

}
