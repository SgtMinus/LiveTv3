package com.live.tv.LiveTv.converter;

import com.live.tv.LiveTv.dto.CommentDto;
import com.live.tv.LiveTv.entity.Comment;
import com.live.tv.LiveTv.entity.CommentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentConverter {
    public Comment toEntity(CommentDto commentDto, Long broadcastId, Long userId) {
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setCommentStatus(CommentStatus.REVIEW);
        comment.setAuthorId(userId);
        comment.setBroadcastId(broadcastId);
        return comment;
    }
}
