package com.ansaradd.notificationservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notification.topics")
@Getter
@Setter
public class NotificationTopicsConfig {
    private String email;
    private String sms;
    private String push;
    private String telegram;
}