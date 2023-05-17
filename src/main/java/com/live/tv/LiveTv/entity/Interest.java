package com.live.tv.LiveTv.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "interests")
@Getter
@Setter
public class Interest extends BaseEntity {

    @Column(name = "interest")
    private Long interest;

    @JoinColumn(name = "user_id")
    private Long userId;

    @JoinColumn(name = "comment_id")
    private Long commentId;
}
