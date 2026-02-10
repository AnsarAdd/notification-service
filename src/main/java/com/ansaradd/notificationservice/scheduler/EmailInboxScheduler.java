package com.ansaradd.notificationservice.scheduler;

import com.ansaradd.notificationservice.inbox.EmailInbox;
import com.ansaradd.notificationservice.repository.EmailInboxRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailInboxScheduler extends AbstractInboxScheduler<EmailInbox, EmailInboxRepository> {

    private final EmailInboxRepository repository;

    public EmailInboxScheduler(EmailInboxRepository repository) {
        this.repository = repository;
    }

    @Override
    protected EmailInboxRepository getRepository() {
        return repository;
    }

    @Override
    protected String getInboxType() {
        return "email";
    }
}