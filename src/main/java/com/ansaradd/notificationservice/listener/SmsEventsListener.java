package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.config.NotificationTopicsConfig;
import com.ansaradd.notificationservice.inbox.SmsInbox;
import com.ansaradd.notificationservice.repository.SmsInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsEventsListener extends AbstractEventsListener<SmsInbox, SmsInboxRepository> {

    private final SmsInboxRepository repository;
    private final NotificationTopicsConfig topicsConfig;

    @Override protected SmsInboxRepository getRepository() { return repository; }

    @Override
    protected SmsInbox createInboxMessage(ConsumerRecord<String, String> record) {
        return SmsInbox.builder()
                .topic(record.topic())
                .key(record.key())
                .value(record.value())
                .processed(false)
                .attempt(1)
                .build();
    }

    @Override public String getTopic() { return topicsConfig.getSms(); }
}