package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.CommentStatus;
import com.live.tv.LiveTv.entity.User;
import com.live.tv.LiveTv.service.db.CommentDbService;
import com.live.tv.LiveTv.service.db.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    private final EmailService emailService;

    @Scheduled(cron = "0 */1 * * * *")
    public void startCourt() {
        List<Comment> comments = commentDbService.findAllByCommentStatus(CommentStatus.APPROVED);
        for (Comment comment : comments) {
            User author = userDbService.findUserById(comment.getAuthorId());
            List<User> users = userDbService.findAllByReputationGreaterThan(author.getReputation()).stream().limit(3).toList();
            for (User judge : users) {
                if (emailMessages.containsKey(judge.getEmail())) {
                    String message = emailMessages.get(judge.getEmail());
                    message = message + "\n" + commentService.getCommentUrl(comment);
                    emailMessages.put(judge.getEmail(), message);
                } else {
                    emailMessages.put(judge.getEmail(), commentService.getCommentUrl(comment));
                }
            }
        }
        emailService.send(emailMessages);
    }

    @Scheduled(cron = "0 */3 * * * *")
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
}
