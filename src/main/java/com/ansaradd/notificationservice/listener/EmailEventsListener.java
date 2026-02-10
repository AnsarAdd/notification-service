package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.inbox.EmailInbox;
import com.ansaradd.notificationservice.repository.EmailInboxRepository;
import com.ansaradd.notificationservice.config.NotificationTopicsConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventsListener extends AbstractEventsListener<EmailInbox, EmailInboxRepository>{

    private final EmailInboxRepository repository;
    private final NotificationTopicsConfig topicsConfig;

    @Override protected EmailInboxRepository getRepository() { return repository; }

    @Override
    protected EmailInbox createInboxMessage(ConsumerRecord<String, String> record) {
        return EmailInbox.builder()
                .topic(record.topic())
                .key(record.key())
                .value(record.value())
                .processed(false)
                .attempt(1)
                .build();
    }

    @Override
    public String getTopic() {
        return topicsConfig.getEmail();
    }
}