package com.ansaradd.notificationservice.scheduler;

import com.ansaradd.notificationservice.inbox.PushInbox;
import com.ansaradd.notificationservice.repository.PushInboxRepository;
import org.springframework.stereotype.Service;

@Service
public class PushInboxScheduler extends AbstractInboxScheduler<PushInbox, PushInboxRepository> {

    private final PushInboxRepository repository;

    public PushInboxScheduler(PushInboxRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PushInboxRepository getRepository() {
        return repository;
    }

    @Override
    protected String getInboxType() {
        return "push";
    }
}