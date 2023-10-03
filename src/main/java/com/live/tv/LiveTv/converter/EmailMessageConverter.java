package com.live.tv.LiveTv.converter;

import com.live.tv.LiveTv.dto.EmailMessageDto;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageConverter {
    public EmailMessageDto convertToDto(String emailTo, String message) {
        EmailMessageDto dto = new EmailMessageDto();
        dto.setEmailTo(emailTo).setMessage(message);
        return dto;
    }
}
