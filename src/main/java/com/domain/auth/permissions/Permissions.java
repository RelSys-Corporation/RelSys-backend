package com.domain.auth.permissions;

import com.domain.shared.BaseEntity;
import com.domain.system.message.Message;
import com.domain.system.message.MessageEntity;
import com.infrastructure.exceptions.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PERMISSIONS", schema = "AUTH")
public class Permissions extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    protected Permissions() {}

    public static Permissions getByIdOrThrow(Long id) {
        if (id == null)
            throw new IllegalArgumentException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.PERMISSION));

        return Permissions.<Permissions>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.PERMISSION, id)));
    }
}
