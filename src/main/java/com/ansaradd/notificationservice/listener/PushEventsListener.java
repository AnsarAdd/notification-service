package com.ansaradd.notificationservice.listener;

import com.ansaradd.notificationservice.inbox.PushInbox;
import com.ansaradd.notificationservice.repository.PushInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PushEventsListener {

    private final PushInboxRepository repository;

    @KafkaListener(
            topics = "push-events",
            groupId = "notification-service"
    )
    public void listen(ConsumerRecord<String, String> record) {
        String messageKey = record.key();
        String messageValue = record.value();
        log.info("=== RECEIVED PUSH MESSAGE ===");
        log.info("Topic: {}, Key: {}, Value: {}",
                record.topic(), messageKey, messageValue);

        if (repository.existsByKeyAndValue(messageKey, messageValue)) {
            log.debug("Дубликат сообщения пропущен: Key: {}, Value: {}",
                    messageKey, messageValue);
            return;
        }

        PushInbox inbox = PushInbox.builder()
                .topic(record.topic())
                .key(messageKey)
                .value(messageValue)
                .processed(false)
                .attempt(1)
                .build();

        repository.save(inbox);
        log.info("Новое сообщение сохранено в PushInbox: Key: {}, Value: {}",
                messageKey, messageValue);
    }
}