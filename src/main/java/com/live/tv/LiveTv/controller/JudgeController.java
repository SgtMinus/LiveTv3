package com.live.tv.LiveTv.controller;

import com.live.tv.LiveTv.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/broadcasts/{broadcastId}/comments/{commentId}/judge")
@AllArgsConstructor
public class JudgeController {
    private final CommentService commentService;

    @PutMapping
    public ResponseEntity<?> giveReview(@PathVariable(name = "broadcastId") Long broadcastId,
                                        @PathVariable(name = "commentId") Long commentId,
                                        @RequestParam(name = "isApproved") boolean isApproved) {
        return ResponseEntity.ok(commentService.giveJudgeReview(commentId, broadcastId, isApproved));
    }
}
