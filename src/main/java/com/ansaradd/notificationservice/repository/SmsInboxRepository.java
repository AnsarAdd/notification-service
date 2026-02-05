package com.ansaradd.notificationservice.repository;

import com.ansaradd.notificationservice.inbox.SmsInbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SmsInboxRepository extends JpaRepository<SmsInbox, UUID> {
    Page<SmsInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);

    boolean existsByKeyAndValue(String key, String value);
}