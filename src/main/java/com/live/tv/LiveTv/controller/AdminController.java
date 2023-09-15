package com.live.tv.LiveTv.controller;

import com.live.tv.LiveTv.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/admin/broadcasts/{broadcastId}/comments")
@AllArgsConstructor
public class AdminController {
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<?>> getBroadcastComments(@PathVariable(name = "broadcastId") @NonNull
                                                        @Min(value = 1, message = "Нумерация трансляций начинается с 1!") Long broadcastId,
                                                        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(commentService.getComments(broadcastId, page, pageSize));
    }

    @GetMapping("/needReview")
    public ResponseEntity<List<?>> getBroadcastCommentsToReview(@PathVariable(name = "broadcastId") @NonNull
                                                                @Min(value = 1, message = "Нумерация трансляций начинается с 1!") Long broadcastId,
                                                                @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(commentService.getNeedReviewComments(broadcastId, page, pageSize));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> giveReview(@PathVariable(name = "broadcastId") @NonNull
                                        @Min(value = 1, message = "Нумерация трансляций начинается с 1!") Long broadcastId,
                                        @PathVariable(name = "commentId") @NonNull
                                        @Min(value = 1, message = "Нумерация комментариев начинается с 1!") Long commentId,
                                        @RequestParam(name = "isApproved") boolean isApproved) {
        return ResponseEntity.ok(commentService.giveReview(commentId, broadcastId, isApproved));
    }
}

