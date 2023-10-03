package com.live.tv.LiveTv.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ToString
public class EmailMessageDto {
    private String emailTo;
    private String message;
}
