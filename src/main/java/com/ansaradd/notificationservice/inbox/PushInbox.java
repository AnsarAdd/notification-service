package com.ansaradd.notificationservice.inbox;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "push_inbox",
        uniqueConstraints = @UniqueConstraint(columnNames = {"key", "value"})
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PushInbox extends BaseInbox{

}