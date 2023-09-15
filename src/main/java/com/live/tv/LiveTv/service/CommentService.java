package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.converter.CommentConverter;
import com.live.tv.LiveTv.dto.CommentDto;
import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.CommentStatus;
import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.exception.EntityNotFoundException;
import com.live.tv.LiveTv.service.db.CommentDbService;
import com.live.tv.LiveTv.service.db.InterestDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final String COMMENT_ENTITY_NAME = "Комментария";
    private final CommentConverter commentConverter;
    private final BroadcastService broadcastService;
    private final CommentDbService commentDbService;
    private final UserService userService;
    private final InterestDbService interestDbService;
    private final CourtService courtService;

    @Autowired
    public CommentService(CommentConverter commentConverter, BroadcastService broadcastService, CommentDbService commentDbService, UserService userService, InterestDbService interestDbService, @Lazy CourtService courtService) {
        this.commentConverter = commentConverter;
        this.broadcastService = broadcastService;
        this.commentDbService = commentDbService;
        this.userService = userService;
        this.interestDbService = interestDbService;
        this.courtService = courtService;
    }

    public List<Comment> getComments(Long broadcastId, int page, int pageSize) {
        broadcastService.checkBroadcastExists(broadcastId);
        return commentDbService.findAllByBroadcastId(broadcastId, PageRequest.of(page, pageSize)).getContent();
    }

    public List<Comment> getNeedReviewComments(Long broadcastId, int page, int pageSize) {
        broadcastService.checkBroadcastExists(broadcastId);
        return commentDbService.findAllByBroadcastIdAndCommentStatus(broadcastId, CommentStatus.REVIEW, PageRequest.of(page, pageSize)).getContent();
    }

    public List<Comment> getApprovedComments(Long broadcastId, int page, int pageSize) {
        broadcastService.checkBroadcastExists(broadcastId);
        return commentDbService.findAllByBroadcastIdAndCommentStatus(broadcastId, CommentStatus.APPROVED, PageRequest.of(page, pageSize)).getContent();
    }

    public Comment addComment(CommentDto commentDto, Long broadcastId) {
        User user = userService.getUserFromContext();
        userService.checkBannedUser(user);
        broadcastService.checkBroadcastExists(broadcastId);
        Comment comment = commentConverter.toEntity(commentDto, broadcastId, user.getId());
        return commentDbService.save(comment);
    }

    public Comment updateComment(Long commentId, Long broadcastId, CommentDto commentDto) {
        User user = userService.getUserFromContext();
        userService.checkBannedUser(user);
        Long userId = user.getId();
        checkExistsByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId);
        Comment comment = commentDbService.findByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId);
        comment.setBody(commentDto.getBody());
        return commentDbService.save(comment);
    }

    public void deleteComment(Long commentId, Long broadcastId) {
        Long userId = userService.getUserIdFromContext();
        checkExistsByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId);
        interestDbService.deleteAllByCommentId(commentId);
        commentDbService.deleteByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId);
    }

    public Comment giveJudgeReview(Long commentId, Long broadcastId, boolean isApproved) throws Exception {
        User judge = userService.getUserFromContext();
        if (commentDbService.existsByIdAndBroadcastId(commentId, broadcastId)) {
            throw new EntityNotFoundException(COMMENT_ENTITY_NAME);
        }
        List<User> judges = courtService.getCommentJudges(commentId);

        if (!judges.stream().map(User::getEmail).toList().contains(judge.getEmail())) {
            throw new Exception("Вы не являетесь судьей по данному процессу");
        }
        Long summaryJudges = courtService.getCommentSummaryJudges(commentId);
        Comment comment = commentDbService.findById(commentId);
        User commentAuthor = userService.getUserById(comment.getAuthorId());
        userService.checkReputationJudgeGreaterAuthor(judge, commentAuthor);
        judges = courtService.removeCommentJudge(commentId, judge);
        long mark = isApproved ? 1 : -1;
        summaryJudges = summaryJudges + mark;
        courtService.setCommentSummaryJudges(commentId, summaryJudges);

        if (judges.isEmpty()) {
            isApproved = courtService.geCommentSummaryJudgesMark(commentId);
            if (!isApproved) {
                userService.setBanToUser(commentAuthor);
            }
            comment.setCommentStatus(isApproved ? CommentStatus.APPROVED : CommentStatus.JUDGE_REJECTED);
            courtService.removeCommentFromJudge(commentId);
        }
        return commentDbService.save(comment);
    }

    public Comment giveReview(Long commentId, Long broadcastId, boolean isApproved) {
        //Long userId = userService.getUserIdFromContext();
        //checkExistsByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId);
        checkExistsByIdAndBroadcastId(commentId, broadcastId);
        //Comment comment = commentDbService.findByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId);
        Comment comment = commentDbService.findById(commentId);
        comment.setCommentStatus(isApproved ? CommentStatus.APPROVED : CommentStatus.REJECTED);
        return commentDbService.save(comment);
    }

    public String getCommentUrl(Comment comment) {
        return "/broadcasts/" + comment.getBroadcastId() + "/comments/" + comment.getId();
    }

    public void checkExistsByIdAndBroadcastIdAndAuthorId(Long commentId, Long broadcastId, Long userId) {
        if (commentDbService.existsByIdAndBroadcastIdAndAuthorId(commentId, broadcastId, userId)) {
            throw new EntityNotFoundException(COMMENT_ENTITY_NAME);
        }
    }

    public void checkExistsByIdAndBroadcastId(Long commentId, Long broadcastId) {
        if (commentDbService.existsByIdAndBroadcastId(commentId, broadcastId)) {
            throw new EntityNotFoundException(COMMENT_ENTITY_NAME);
        }
    }
}
