package com.live.tv.LiveTv.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "broadcasts")
@Getter
@Setter
public class Broadcast extends BaseEntity {

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;
}
