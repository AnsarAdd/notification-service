package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.inbox.EmailInbox;
import com.ansaradd.notificationservice.repository.EmailInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailEventsListener {

    private final EmailInboxRepository repository;

    @KafkaListener(
            topics = "email-events",
            groupId = "notification-service"
    )
    public void listen(ConsumerRecord<String, String> record) {
        String messageKey = record.key();
        String messageValue = record.value();
        log.info("=== RECEIVED EMAIL MESSAGE ===");
        log.info("Topic: {}, Key: {}, Value: {}",
                record.topic(), messageKey, messageValue);

        if (repository.existsByKeyAndValue(messageKey, messageValue)) {
            log.debug("Дубликат сообщения пропущен: Key: {}, Value: {}",
                    messageKey, messageValue);
            return;
        }

        EmailInbox inbox = EmailInbox.builder()
                .topic(record.topic())
                .key(messageKey)
                .value(messageValue)
                .processed(false)
                .attempt(1)
                .build();

        repository.save(inbox);
        log.info("Новое сообщение сохранено в EmailInbox: Key: {}, Value: {}",
                messageKey, messageValue);
    }
}