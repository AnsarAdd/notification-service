package com.ansaradd.notificationservice.listener;


import com.ansaradd.notificationservice.inbox.BaseInbox;
import com.ansaradd.notificationservice.repository.BaseInboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public abstract class AbstractEventsListener<
        T extends BaseInbox,
        R extends BaseInboxRepository<T>> {

    protected abstract R getRepository();
    protected abstract T createInboxMessage(ConsumerRecord<String, String> record);
    public abstract String getTopic();

    @KafkaListener(topics = "#{__listener.topic}", groupId = "notification-service1")
    public void listen(ConsumerRecord<String, String> record) {
        String messageKey = record.key();
        String messageValue = record.value();
        log.info("=== RECEIVED {} MESSAGE ===",
                getTopic().toUpperCase().replace("-EVENTS", ""));
        log.info("Topic: {}, Key: {}, Value: {}",
                record.topic(), messageKey, messageValue);

        if (getRepository().existsByKeyAndValue(messageKey, messageValue)) {
            log.info("Дубликат сообщения пропущен: Key: {}, Value: {}",
                    messageKey, messageValue);
            return;
        }

        T inbox = createInboxMessage(record);
        getRepository().save(inbox);
        log.info("Новое сообщение сохранено: Key: {}, Value: {}",
                messageKey, messageValue);
    }
}