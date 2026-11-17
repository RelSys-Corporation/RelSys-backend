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

        Message getMessage() {
            return Message.getByNameOrThrow(this.toString());
        }
    }

    public enum Entity {
        MESSAGE,
        ROLE,
        PERMISSION,
        USER,
        FIRST_USER,
        COMPANY,
        PRODUCT,
        PRODUCT_SUPPLIER,
        PRODUCT_BATCH,
        WAREHOUSE,
        WAREHOUSE_LOCATION,
        SALE,
        SALE_STATUS,
        PAYMENT_METHOD,
        PERSON,
        PERSON_STATUS,
        CPF,
        CNPJ;

        Entity(){}

        public MessageEntity getEntity() {
            return MessageEntity.getByNameOrThrow(this.toString());
        }
    }

    public enum Property {
        NONE,
        NAME,
        PRICE,
        AMOUNT,
        DATA;

        MessageProperty getProperty() {
            return MessageProperty.getByNameOrThrow(this.toString());
        }
    }

    private static String baseMessageResolver(
            MessageEntity entity,
            MessageProperty property,
            boolean noArticle
    ) {
        String baseMessage;

        if (property == null) {
            if (noArticle) baseMessage = entity.label;
            else baseMessage = String.format("%s %s", entity.article, entity.label);
        } else {
            baseMessage = String.format(
                    "%s %s %s%s %s",
                    property.article,
                    property.label,
                    property.suffix,
                    entity.article,
                    entity.label);
        }

        return baseMessage;
    }

    public static String format(
            Type type,
            Message.Entity entityIdentifier,
            Message.Property propertyIdentifier,
            Object... args
    ) {
        Message message = type.getMessage();
        MessageEntity entity = entityIdentifier.getEntity();
        MessageProperty property = (propertyIdentifier == null || propertyIdentifier == Property.NONE) ? null : propertyIdentifier.getProperty();

        Object[] allArgs = new Object[args.length + 1];

        allArgs[0] = baseMessageResolver(entity, property, type.noArticle);

        System.arraycopy(args, 0, allArgs, 1, args.length);
        String formatedMessage = MessageFormat.format(message.content.replace("'", "''"), allArgs);

        return formatedMessage.substring(0, 1).toUpperCase() + formatedMessage.substring(1);
    }

    public static String format(
            Type type,
            Message.Entity entityIdentifier,
            Object... args
    ) {
        return format(type, entityIdentifier, null, args);
    }

        private static Message getByNameOrThrow(String name) {
        return Message.<Message>find("name = ?1", name)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("This message does not exists."));
    }
}
