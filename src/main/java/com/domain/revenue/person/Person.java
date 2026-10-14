package com.domain.revenue.person;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "PERSON", schema = "REVENUE")
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
            throw new DomainException("O nome deve ser informado.");

        if (personTypeAcronym == null || personTypeAcronym.isBlank()) {
            throw new DomainException("Deve ser informado o tipo da pessoa.");
        } else if (!List.of("L", "P", "D").contains(personTypeAcronym)) {
            throw new DomainException("Tipo de pessoa inválido.");
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

    public String getCompanyId() {
        return this.companyId;
    }
}
