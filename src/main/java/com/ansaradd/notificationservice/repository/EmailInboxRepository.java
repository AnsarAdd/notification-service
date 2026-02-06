package com.ansaradd.notificationservice.repository;

import com.ansaradd.notificationservice.inbox.EmailInbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailInboxRepository extends JpaRepository<EmailInbox, UUID> {
    Page<EmailInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);

    boolean existsByKeyAndValue(String key, String value);
}