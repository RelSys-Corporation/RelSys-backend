package com.domain.crm.person;

import com.domain.shared.BaseEntityCompany;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
@Table(name = "PERSON", schema = "CRM")
public class Person extends BaseEntityCompany {
    @Column(name = "NAME", length = 200, nullable = false)
    public String name;

    @Column(name = "EMAIL", length = 255)
    public String email;

    @Column(name = "PERSON_TYPE_ACRONYM", length = 2)
    public String personTypeAcronym;

    protected Person() {}

    protected Person(
            String name,
            @Email
            String email,
            String personTypeAcronym
    ) {
        if (name == null || name.isBlank())
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.PERSON, Message.Property.NAME));

        if (personTypeAcronym == null || personTypeAcronym.isBlank() || !List.of("L", "P", "D").contains(personTypeAcronym)) {
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.PERSON));
        }

        this.name = name;
        this.email = email;
        this.personTypeAcronym = personTypeAcronym;
    }

    public static Person create(
            String name,
            String email,
            String personTypeAcronym
    ) {
        Person person = new @Valid Person(
                name,
                email,
                personTypeAcronym
        );
        person.persist();

        return person;
    }
}
