package com.domain.system.message;

import com.domain.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MESSAGE_ENTITY", schema = "SYSTEM")
public class MessageEntity extends BaseEntity {
    @Column(name = "NAME", nullable = false, updatable = false)
    public String name;

    @Column(name = "LABEL", nullable = false, updatable = false)
    public String label;

    @Column(name = "ARTICLE", nullable = false, updatable = false)
    public String article;

    protected MessageEntity() {}

    public static MessageEntity getByNameOrThrow(String name) {
        return MessageEntity.<MessageEntity>find("name = ?1", name)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("The Message Entity does not exists."));
    }
}