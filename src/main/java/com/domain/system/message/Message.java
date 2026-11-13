package com.domain.system.message;

import com.domain.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.text.MessageFormat;

@Entity
@Table(name = "MESSAGE", schema = "SYSTEM")
public final class Message extends BaseEntity {
    @Column(name = "NAME", nullable = false, updatable = false, unique = true)
    public String name;

    @Column(name = "CONTENT", nullable = false, updatable = false)
    public String content;

    protected Message() {}

    public enum Type {
        REQUIRED_ARGUMENT,
        IDENTIFIER_ABSENT,
        NOT_FOUND,
        ALREADY_EXISTS,
        SHOULD_HAVE
    }

    public static String format(Type type, Object... args) {
        Message message = Message.getByNameOrThrow(type.toString());
        return MessageFormat.format(message.content, args);
    }

    private static Message getByNameOrThrow(String name) {
        return Message.<Message>find("name = ?1", name)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException(
                        Message.format(Type.NOT_FOUND, "mensagem", name))
                );
    }
}
