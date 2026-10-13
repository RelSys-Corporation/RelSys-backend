package com.domain.revenue.person.subtype;

import com.domain.revenue.person.Person;
import com.domain.shared.valueObjects.cpf.CPF;
import com.domain.shared.valueObjects.cpf.CpfAttributeConverter;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

@Entity
@Table(name = "PHYSICAL_PERSON", schema = "REVENUE")
@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
@DiscriminatorValue("P")
public class PhysicalPerson extends Person {
    @Convert(converter = CpfAttributeConverter.class)
    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    public CPF cpf;

    protected PhysicalPerson() {}

    protected PhysicalPerson(
            String name,
            String email,
            CPF cpf
    ) {
        super(name, email);

        if (cpf == null)
            throw new DomainException("O CPF deve ser informado.");

        this.cpf = cpf;
    }

    public static PhysicalPerson create(
            String name,
            String email,
            CPF cpf
    ) {
        PhysicalPerson person = new PhysicalPerson(
                name,
                email,
                cpf
        );

        person.persist();

        return person;
    }
}
