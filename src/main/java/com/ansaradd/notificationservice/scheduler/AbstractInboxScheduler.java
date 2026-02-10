package com.ansaradd.notificationservice.scheduler;

import com.ansaradd.notificationservice.inbox.BaseInbox;
import com.ansaradd.notificationservice.repository.BaseInboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractInboxScheduler<T extends BaseInbox, R extends BaseInboxRepository<T>> {

    @Value("${inbox.batch-size}")
    protected int batchSize;

    @Value("${inbox.delay-ms}")
    protected long delayMs;

    @Value("${inbox.max-attempts:3}")
    protected int maxAttempts;

    protected abstract R getRepository();
    protected abstract String getInboxType();

    @Transactional
    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processInbox() {
        List<T> messages = getRepository()
                .findByProcessedFalseOrderByCreatedAtAsc(PageRequest.of(0, batchSize))
                .getContent();

        for (T msg : messages) {
            try {
                log.info("Обработано {} событие: Key: {}, Payload: {}, topic: {}",
                        getInboxType(), msg.getKey(), msg.getValue(), msg.getTopic());
                msg.setProcessed(true);
                getRepository().save(msg);
            } catch (Exception e) {
                handleError(msg, e);
            }
        }
    }

    private void handleError(T msg, Exception e) {
        int newAttempt = msg.getAttempt() + 1;
        msg.setAttempt(newAttempt);

        if (newAttempt >= maxAttempts) {
            log.error("{} сообщение достигло лимита попыток: {}", getInboxType(), msg.getId());
            msg.setProcessed(true);
            log.warn("Ошибка обработки {} сообщения Key: {}. Attempt: {}. Ошибка: {}",
                    getInboxType(), msg.getKey(), newAttempt, e.getMessage());
        }

        getRepository().save(msg);
    }
}
