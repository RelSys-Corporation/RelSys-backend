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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERMISSION_ENTITY_ID", insertable = false, updatable = false)
    public PermissionsEntities entity;

    @Column(name = "METHOD_NAME", insertable = false, updatable = false)
    public String methodName;

    protected Permissions() {}

    public static Permissions getByIdOrThrow(Long id) {
        if (id == null)
            throw new IllegalArgumentException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.PERMISSION));

        return Permissions.<Permissions>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.PERMISSION, id)));
    }
}
