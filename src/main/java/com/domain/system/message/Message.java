package com.domain.system.message;

import com.domain.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Set;

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
        NOT_FOUND(true),
        ALREADY_EXISTS,
        SHOULD_HAVE;

        boolean noArticle;

        Type() {}

        Type(boolean noArticle) {
            this.noArticle = noArticle;
        }
    }

    public enum Entity {
        MESSAGE,
        ROLE,
        PERMISSION,
        USER,
        FIRST_USER,
        COMPANY,
        PRODUCT;

        Entity(){}

        public MessageEntity getEntity() {
            return MessageEntity.getByNameOrThrow(this.toString());
        }
    }

    public static String format(Type type, Entity entityIdentifier, Object... args) {
        Message message = Message.getByNameOrThrow(type.toString());
        MessageEntity entity = entityIdentifier.getEntity();

        Object[] allArgs = new Object[args.length + 1];
        allArgs[0] = type.noArticle ? entity.label : String.format("%s %s", entity.article, entity.label);

        System.arraycopy(args, 0, allArgs, 1, args.length);
        String formatedMessage = MessageFormat.format(message.content.replace("'", "''"), allArgs);

        return formatedMessage.substring(0, 1).toUpperCase() + formatedMessage.substring(1);
    }

    private static Message getByNameOrThrow(String name) {
        return Message.<Message>find("name = ?1", name)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("This message does not exists."));
    }
}
