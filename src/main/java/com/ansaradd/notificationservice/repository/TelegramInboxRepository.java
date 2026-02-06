package com.ansaradd.notificationservice.repository;

import com.ansaradd.notificationservice.inbox.TelegramInbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TelegramInboxRepository extends JpaRepository<TelegramInbox, UUID>  {
    Page<TelegramInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);

    boolean existsByKeyAndValue(String key, String value);
}
