package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.converter.InterestConverter;
import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.Interest;
import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.exception.InterestException;
import com.live.tv.LiveTv.service.db.CommentDbService;
import com.live.tv.LiveTv.service.db.InterestDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InterestService {
    private final String COMMENT_ENTITY_NAME = "Комментария";

    private final CommentDbService commentDbService;
    private final InterestDbService interestDbService;
    private final InterestConverter interestConverter;
    private final UserService userService;

    @Transactional
    public Interest setInterest(Long commentId, Long broadcastId, long interestValue) {
        User user = userService.getUserFromContext();
        if (interestValue < -1 || interestValue > 1) {
            throw new InterestException();
        }
        if (commentDbService.existsByIdAndBroadcastId(commentId, broadcastId)) {
            throw new EntityNotFoundException(COMMENT_ENTITY_NAME);
        }
        Interest interest;
        Comment comment = commentDbService.findById(commentId);
        if (interestDbService.existsByCommentIdAndUserId(commentId, user.getId())) {
            interest = interestDbService.findByCommentIdAndUserId(commentId, user.getId());
            userService.changeInterest(comment.getAuthorId(), interestValue, interest.getInterest());
            interest.setInterest(interestValue);
        } else {
            interest = interestConverter.toEntity(commentId, interestValue);
            userService.changeInterest(comment.getAuthorId(), interestValue);
        }
        return interestDbService.save(interest);
    }

    public void deleteInterest(Long commentId, Long broadcastId, Long interestId) {
        Long userId = userService.getUserIdFromContext();
        if (commentDbService.existsByIdAndBroadcastId(commentId, broadcastId)) {
            throw new EntityNotFoundException(COMMENT_ENTITY_NAME);
        }
        Interest interest = interestDbService.findByIdAndUserId(interestId, userId);
        Comment comment = commentDbService.findById(commentId);
        userService.changeInterest(comment.getAuthorId(), -interest.getInterest());
        interestDbService.delete(interest);
    }
}
