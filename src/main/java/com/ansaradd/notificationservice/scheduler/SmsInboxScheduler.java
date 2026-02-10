package com.ansaradd.notificationservice.scheduler;

import com.ansaradd.notificationservice.inbox.SmsInbox;
import com.ansaradd.notificationservice.repository.SmsInboxRepository;
import org.springframework.stereotype.Service;

@Service
public class SmsInboxScheduler extends AbstractInboxScheduler<SmsInbox, SmsInboxRepository> {

    private final SmsInboxRepository repository;

    public SmsInboxScheduler(SmsInboxRepository repository) {
        this.repository = repository;
    }

    @Override protected SmsInboxRepository getRepository() { return repository; }

    @Override protected String getInboxType() { return "sms"; }
}