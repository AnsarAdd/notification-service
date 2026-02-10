package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.config.NotificationTopicsConfig;
import com.ansaradd.notificationservice.inbox.TelegramInbox;
import com.ansaradd.notificationservice.repository.TelegramInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramEventsListener extends AbstractEventsListener<TelegramInbox, TelegramInboxRepository> {

    private final TelegramInboxRepository repository;
    private final NotificationTopicsConfig topicsConfig;

    @Override protected TelegramInboxRepository getRepository() { return repository; }

    @Override
    protected TelegramInbox createInboxMessage(ConsumerRecord<String, String> record) {
        return TelegramInbox.builder()
                .topic(record.topic())
                .key(record.key())
                .value(record.value())
                .processed(false)
                .attempt(1)
                .build();
    }

    @Override public String getTopic() { return topicsConfig.getTelegram(); }
}