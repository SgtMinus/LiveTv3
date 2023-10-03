package com.live.tv.LiveTv.service.activemq;

import com.live.tv.LiveTv.dto.EmailMessageDto;
import com.live.tv.LiveTv.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ListenerService {
    private EmailService emailService;

    @JmsListener(destination = "emails")
    public void listen(EmailMessageDto dto) {
        log.info(dto.toString());
        emailService.send(dto.getEmailTo(), dto.getMessage());
    }
}
