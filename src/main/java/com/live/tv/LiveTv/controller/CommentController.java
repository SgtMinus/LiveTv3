package com.live.tv.LiveTv.controller;

import com.live.tv.LiveTv.dto.CommentDto;
import com.live.tv.LiveTv.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/broadcasts/{broadcastId}/comments")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<?>> getApprovedBroadcastComments(@PathVariable(name = "broadcastId") Long broadcastId,
                                                                @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(commentService.getApprovedComments(broadcastId, page, pageSize));
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable(name = "broadcastId") Long broadcastId,
                                        @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(commentDto, broadcastId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "commentId") Long commentId,
                                           @PathVariable(name = "broadcastId") Long broadcastId
    ) {
        commentService.deleteComment(commentId, broadcastId);
        return ResponseEntity.ok("Cущность удалена из бд!!!");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "commentId") Long commentId,
                                           @PathVariable(name = "broadcastId") Long broadcastId,
                                           @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, broadcastId, commentDto));
    }
}

