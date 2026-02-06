package com.ansaradd.notificationservice.repository;

import com.ansaradd.notificationservice.inbox.PushInbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PushInboxRepository extends JpaRepository<PushInbox, UUID>  {
    Page<PushInbox> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);

    boolean existsByKeyAndValue(String key, String value);
}
