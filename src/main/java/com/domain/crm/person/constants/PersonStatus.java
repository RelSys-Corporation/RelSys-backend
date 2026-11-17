package com.domain.crm.person.constants;

import com.domain.shared.BaseEntity;
import com.domain.system.message.Message;
import jakarta.persistence.*;

@Entity
@Table(name = "PERSON_STATUS", schema = "CRM")
public class PersonStatus extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    public String name;

    public enum Type {
        ACTIVE(1L, "ATIVO"),
        INACTIVE(2L, "INATIVO");

        private final Long id;
        private final String description;

        Type(Long id, String description) {
            this.id = id;
            this.description = description;
        }

        public Long getId() {
            return this.id;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public static PersonStatus of(Type status) {
        return PersonStatus.<PersonStatus>findByIdOptional(status.getId())
                .orElseThrow(() -> new IllegalStateException(Message.format(Message.Type.NOT_FOUND, Message.Entity.PERSON, Message.Property.STATUS, status.description)));
    }
}