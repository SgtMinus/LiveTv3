package com.live.tv.LiveTv.service.activemq;

import com.live.tv.LiveTv.converter.EmailMessageConverter;
import com.live.tv.LiveTv.dto.EmailMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ProducerService {
    private EmailMessageConverter emailMessageConverter;
    private final JmsTemplate jmsTemplate;

    public void send(Map<String, String> emailMessages) {
        for (String emailTo : emailMessages.keySet()) {
            EmailMessageDto dto = emailMessageConverter.convertToDto(emailTo, emailMessages.get(emailTo));
            jmsTemplate.convertAndSend("emails", dto);
        }
    }
}
