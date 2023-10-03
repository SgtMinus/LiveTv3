package com.live.tv.LiveTv.service;

import com.live.tv.LiveTv.config.EmailConfig;
import com.live.tv.LiveTv.converter.EmailMessageConverter;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;

    public void send(String emailTo, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailConfig.getUsername());
        mailMessage.setTo(emailTo);
        mailMessage.setSubject("Вынесите вердикт!");
        mailMessage.setText("Подозрительные комментарии:\n" + message);
        mailSender.send(mailMessage);
    }

}
