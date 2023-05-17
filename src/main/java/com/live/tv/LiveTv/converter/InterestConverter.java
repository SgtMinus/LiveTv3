package com.live.tv.LiveTv.converter;

import com.live.tv.LiveTv.entity.Interest;
import com.live.tv.LiveTv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InterestConverter {
    private final UserService userService;

    public Interest toEntity(Long commentId, long interestValue) {
        Interest interest = new Interest();
        interest.setInterest(interestValue);
        interest.setCommentId(commentId);
        interest.setUserId(userService.getUserIdFromContext());
        return interest;
    }
}
