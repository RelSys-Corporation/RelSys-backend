package com.domain.revenue.person;

import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "PERSON", schema = "REVENUE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "PERSON_TYPE_ACRONYM",
        discriminatorType = DiscriminatorType.STRING,
        length = 2
)
@DiscriminatorValue("D")
public class Person extends BaseEntityCompany {
    @Column(name = "NAME", length = 200, nullable = false)
    public String name;

    @Column(name = "EMAIL", length = 255)
    public String email;

    @Column(name = "PERSON_TYPE_ACRONYM", length = 2, insertable = false, updatable = false)
    public String personTypeAcronym;

    protected Person() {}

    protected Person(
            String name,
            @Email
            String email
    ) {
        if (name == null || name.isBlank())
            throw new DomainException("O nome deve ser informado.");

        this.name = name;
        this.email = email;
    }

    public static Person create(
            String name,
            String email
    ) {
        Person person = new @Valid Person(
                name,
                email
        );
        person.persist();

        return person;
    }

    public String getCompanyId() {
        return this.companyId;
    }
}
