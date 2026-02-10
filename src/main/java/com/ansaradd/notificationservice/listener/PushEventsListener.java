package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.config.NotificationTopicsConfig;
import com.ansaradd.notificationservice.inbox.PushInbox;
import com.ansaradd.notificationservice.repository.PushInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PushEventsListener extends AbstractEventsListener<PushInbox, PushInboxRepository> {

    private final PushInboxRepository repository;
    private final NotificationTopicsConfig topicsConfig;

    @Override protected PushInboxRepository getRepository() { return repository; }

    @Override
    protected PushInbox createInboxMessage(ConsumerRecord<String, String> record) {
        return PushInbox.builder()
                .topic(record.topic())
                .key(record.key())
                .value(record.value())
                .processed(false)
                .attempt(1)
                .build();
    }

    @Override public String getTopic() { return topicsConfig.getPush(); }
}