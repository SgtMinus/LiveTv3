package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.CommentStatus;
import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.service.activemq.ProducerService;
import com.live.tv.LiveTv.service.db.CommentDbService;
import com.live.tv.LiveTv.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CourtService {
    private final Map<String, String> emailMessages = new HashMap<>();
    private final CommentDbService commentDbService;
    private final CommentService commentService;
    private final UserDbService userDbService;
    private final UserService userService;
    private final Map<Long, List<User>> commentJudges = new HashMap<>();
    private final Map<Long, Long> commentSummaryJudges = new HashMap<>();
    private final Long DEFAULT_JUDGE_COUNT = 3L;
    private final ProducerService producerService;

    public void startCourt() {
        List<Comment> comments = commentDbService.findAllByCommentStatus(CommentStatus.APPROVED);
        comments.addAll(commentDbService.findAllByCommentStatus(CommentStatus.REVIEW));
        for (Comment comment : comments) {
            User author = userDbService.findUserById(comment.getAuthorId());
            List<User> users = new java.util.ArrayList<>(List.of());
            List<User> alreadyJudges = getCommentJudges(comment.getId());
            List<User> userGreaterReputation = userDbService.findAllByReputationGreaterThan(author.getReputation());
            Collections.shuffle(userGreaterReputation);
            if (alreadyJudges.size() == 0) {
                users.addAll(userGreaterReputation.stream().limit(DEFAULT_JUDGE_COUNT).toList());
            } else {
                int countOfJudges = alreadyJudges.size();
                users.addAll(userGreaterReputation.stream().limit(countOfJudges).toList());
            }
            addCommentJudges(comment.getId(), users);
            for (User judge : users) {
                if (emailMessages.containsKey(judge.getEmail())) {
                    String message = emailMessages.get(judge.getEmail());
                    message = message + "\n" + commentService.getCommentUrl(comment);
                    emailMessages.put(judge.getEmail(), message);
                } else {
                    emailMessages.put(judge.getEmail(), commentService.getCommentUrl(comment));
                }
            }
            comment.setCommentStatus(CommentStatus.REVIEW);
            commentDbService.save(comment);
        }

        producerService.send(emailMessages);
    }

    public void recoverPosts() {
        List<Comment> comments = commentDbService.findAllByCommentStatus(CommentStatus.JUDGE_REJECTED);
        for (Comment comment : comments) {
            User user = userService.getUserById(comment.getAuthorId());
            if (userService.isBannedUser(user)) {
                continue;
            }
            comment.setCommentStatus(CommentStatus.APPROVED);
        }
        commentDbService.saveAll(comments);
    }

    public List<User> getCommentJudges(Long commentId) {
        return commentJudges.getOrDefault(commentId, List.of());
    }

    public void removeCommentFromJudge(Long commentId) {
        commentJudges.remove(commentId);
        commentSummaryJudges.remove(commentId);
    }

    public Long getCommentSummaryJudges(Long commentId) {
        return commentSummaryJudges.getOrDefault(commentId, 0L);
    }

    public void setCommentSummaryJudges(Long commentId, Long summary) {
        commentSummaryJudges.put(commentId, summary);
    }

    public boolean geCommentSummaryJudgesMark(Long commentId) {
        return commentSummaryJudges.getOrDefault(commentId, 0L) >= 0;
    }

    public List<User> removeCommentJudge(Long commentId, User user) {
        List<User> judges = getCommentJudges(commentId);
        judges = judges.stream().filter(j -> !j.getEmail().equals(user.getEmail())).toList();
        commentJudges.put(commentId, judges);
        return getCommentJudges(commentId);
    }

    public void addCommentJudges(Long commentId, List<User> judges) {
        commentJudges.put(commentId, judges);
    }
}
