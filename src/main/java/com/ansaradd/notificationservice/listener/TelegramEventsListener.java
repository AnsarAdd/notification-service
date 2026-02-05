package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.inbox.TelegramInbox;
import com.ansaradd.notificationservice.repository.TelegramInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramEventsListener {

    private final TelegramInboxRepository repository;

    @KafkaListener(
            topics = "telegram-events",
            groupId = "notification-service"
    )
    public void listen(ConsumerRecord<String, String> record) {
        String messageKey = record.key();
        String messageValue = record.value();
        log.info("=== RECEIVED TELEGRAM MESSAGE ===");
        log.info("Topic: {}, Key: {}, Value: {}",
                record.topic(), messageKey, messageValue);

        if (repository.existsByKeyAndValue(messageKey, messageValue)) {
            log.debug("Дубликат сообщения пропущен: Key: {}, Value: {}",
                    messageKey, messageValue);
            return;
        }

        TelegramInbox inbox = TelegramInbox.builder()
                .topic(record.topic())
                .key(messageKey)
                .value(messageValue)
                .processed(false)
                .attempt(1)
                .build();

        repository.save(inbox);
        log.info("Новое сообщение сохранено в TelegramInbox: Key: {}, Value: {}",
                messageKey, messageValue);
    }
}