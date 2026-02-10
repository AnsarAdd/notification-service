package com.ansaradd.notificationservice.repository;


import com.ansaradd.notificationservice.inbox.BaseInbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseInboxRepository<T extends BaseInbox> extends JpaRepository<T, UUID> {
    Page<T> findByProcessedFalseOrderByCreatedAtAsc(Pageable pageable);
    boolean existsByKeyAndValue(String key, String value);
}