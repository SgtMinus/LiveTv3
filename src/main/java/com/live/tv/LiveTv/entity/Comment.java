package com.live.tv.LiveTv.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity {

    @Column(name = "body")
    private String body;

    @JoinColumn(name = "author_id")
    private Long authorId;

    @JoinColumn(name = "broadcast_id")
    private Long broadcastId;


    @Column(name = "comment_status")
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.REVIEW;
}
