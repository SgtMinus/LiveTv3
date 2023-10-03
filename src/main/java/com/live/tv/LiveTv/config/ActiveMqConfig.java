package com.live.tv.LiveTv.config;

import com.live.tv.LiveTv.dto.EmailMessageDto;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Collections;

@Configuration
public class ActiveMqConfig {
    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        val messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("content-type");
        messageConverter.setTypeIdMappings(Collections.singletonMap("email", EmailMessageDto.class));
        return messageConverter;
    }
}
