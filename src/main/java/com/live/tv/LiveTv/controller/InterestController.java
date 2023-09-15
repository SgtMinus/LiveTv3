package com.live.tv.LiveTv.controller;

import com.live.tv.LiveTv.service.InterestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/broadcasts/{broadcastId}/comments/{commentId}/interest")
@AllArgsConstructor
public class InterestController {
    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<?> setInterest(@PathVariable(name = "commentId") Long commentId,
                                         @PathVariable(name = "broadcastId") Long broadcastId,
                                         @RequestParam(name = "interest") long interest) {
        return ResponseEntity.ok(interestService.setInterest(commentId, broadcastId, interest));
    }

    @DeleteMapping
    public ResponseEntity<?> setInterest(@PathVariable(name = "commentId") Long commentId,
                                         @PathVariable(name = "broadcastId") Long broadcastId,
                                         @RequestParam(name = "interestId") Long interestId) {
        interestService.deleteInterest(commentId, broadcastId, interestId);
        return ResponseEntity.ok("Cущность удалена из бд!!!");
    }
}

