package com.domain.system.message;

import com.domain.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MESSAGE_PROPERTY", schema = "SYSTEM")
public class MessageProperty extends BaseEntity {
    @Column(name = "NAME", insertable = false, updatable = false)
    public String name;

    @Column(name = "LABEL", insertable = false, updatable = false)
    public String label;

    @Column(name = "ARTICLE", insertable = false, updatable = false)
    public String article;

    @Column(name = "SUFFIX", insertable = false, updatable = false)
    public String suffix;

    public static MessageProperty getByNameOrThrow(String name) {
        return MessageProperty.<MessageProperty>find("name = ?1", name)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("This message property does not exists."));
    }
}
