package com.ansaradd.notificationservice.scheduler;

import com.ansaradd.notificationservice.inbox.EmailInbox;
import com.ansaradd.notificationservice.repository.EmailInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailInboxScheduler {

    private final EmailInboxRepository repository;

    @Value("${inbox.batch-size}")
    private int batchSize;

    @Value("${inbox.delay-ms}")
    private long delayMs;

    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processInbox() {
        List<EmailInbox> messages =
                repository.findByProcessedFalseOrderByCreatedAtAsc(
                PageRequest.of(0, batchSize)
        ).getContent();

        for (EmailInbox msg : messages) {
            try {
                log.info("Обработано событие: Key: {}, Payload: {}, topic: {}", msg.getKey(), msg.getValue(), msg.getTopic());
                msg.setProcessed(true);
            } catch (Exception e) {
                msg.setAttempt(msg.getAttempt() + 1);
                log.error("Ошибка обработки сообщения Key: {}. Attempt: {}. Ошибка: {}", msg.getKey(), msg.getAttempt(), e.getMessage());
            } finally {
                repository.save(msg);
            }
        }
    }
}