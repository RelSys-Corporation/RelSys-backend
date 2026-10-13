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
        return Person.create(
                name,
                email
        );
    }

    public LegalPerson createLegalPerson(
            String name,
            String email,
            String cnpj
    ) {
        return LegalPerson.create(
                name,
                email,
                new CNPJ(cnpj)
        );
    }

    public PhysicalPerson createPhysicalPerson(
            String name,
            String email,
            String cpf
    ) {
        return PhysicalPerson.create(
                name,
                email,
                new CPF(cpf)
        );
    }
}