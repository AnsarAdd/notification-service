package com.ansaradd.notificationservice.scheduler;

import com.ansaradd.notificationservice.inbox.TelegramInbox;
import com.ansaradd.notificationservice.repository.TelegramInboxRepository;

import org.springframework.stereotype.Service;


@Service
public class TelegramInboxScheduler extends AbstractInboxScheduler<TelegramInbox, TelegramInboxRepository> {

    private final TelegramInboxRepository repository;

    public TelegramInboxScheduler(TelegramInboxRepository repository) {
        this.repository = repository;
    }

    @Override protected TelegramInboxRepository getRepository() { return repository; }
    @Override protected String getInboxType() { return "telegram"; }
}