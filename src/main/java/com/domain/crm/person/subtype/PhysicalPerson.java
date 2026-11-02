package com.domain.revenue.person.subtype;

import com.domain.revenue.person.Person;
import com.domain.shared.BaseEntityCompany;
import com.domain.shared.valueObjects.cpf.CPF;
import com.domain.shared.valueObjects.cpf.CpfAttributeConverter;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

@Entity
@Table(name = "PHYSICAL_PERSON", schema = "REVENUE")
public class PhysicalPerson extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    public Person person;

    @Column(name = "PERSON_TYPE_ACRONYM", length = 2, insertable = false, updatable = false, nullable = false)
    public String personTypeAcronym;

    @Convert(converter = CpfAttributeConverter.class)
    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    public CPF cpf;

    protected PhysicalPerson() {}

    protected PhysicalPerson(
            Person person,
            CPF cpf
    ) {
        if (person == null)
            throw new DomainException("A pessoa base deve ser informada.");

        if (cpf == null)
            throw new DomainException("O CPF deve ser informado.");

        this.person = person;
        this.cpf = cpf;
    }

    public static PhysicalPerson create(
            Person person,
            CPF cpf
    ) {
        PhysicalPerson PhysicalPerson = new PhysicalPerson(
                person,
                cpf
        );

        PhysicalPerson.persist();

        return PhysicalPerson;
    }
}
