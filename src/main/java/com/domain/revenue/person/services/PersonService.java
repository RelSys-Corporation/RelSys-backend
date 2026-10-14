package com.domain.revenue.person.services;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.subtype.LegalPerson;
import com.domain.revenue.person.subtype.PhysicalPerson;
import com.domain.shared.valueObjects.cnpj.CNPJ;
import com.domain.shared.valueObjects.cpf.CPF;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PersonService {
    public Person createDigitalPerson(
            String name,
            String email
    ) {
        Person person = Person.create(
                name,
                email,
                "D"
        );

        if (person.id == null)
            throw new IllegalStateException("Não foi possível criar a pessoa digital.");

        return person;

    }

    public LegalPerson createLegalPerson(
            String name,
            String email,
            String cnpj
    ) {
        Person person = Person.create(
                name,
                email,
                "L"
        );

        LegalPerson legalPerson = LegalPerson.create(
                person,
                new CNPJ(cnpj)
        );

        if (legalPerson.id == null)
            throw new IllegalStateException("Não foi possível criar a pessoa juridica.");

        return legalPerson;
    }

    public PhysicalPerson createPhysicalPerson(
            String name,
            String email,
            String cpf
    ) {
        Person person = Person.create(
                name,
                email,
                "P"
        );

        PhysicalPerson physicalPerson = PhysicalPerson.create(
                person,
                new CPF(cpf)
        );

        if (physicalPerson.id == null)
            throw new IllegalStateException("Não foi possível criar a pessoa fisica.");

        return physicalPerson;
    }
}