package com.domain.crm.person.subtype;

import com.domain.crm.person.Person;
import com.domain.shared.BaseEntityCompany;
import com.domain.shared.valueObjects.cnpj.CNPJ;
import com.domain.shared.valueObjects.cnpj.CnpjAttributeConverter;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

@Entity
@Table(name = "LEGAL_PERSON", schema = "CRM")
public class LegalPerson extends BaseEntityCompany {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    public Person person;

    @Column(name = "PERSON_TYPE_ACRONYM", length = 2, insertable = false, updatable = false, nullable = false)
    public String personTypeAcronym;

    @Convert(converter = CnpjAttributeConverter.class)
    @Column(name = "CNPJ", length = 14, nullable = false, unique = true)
    public CNPJ cnpj;

    protected LegalPerson() {}

    protected LegalPerson(
            Person person,
            CNPJ cnpj
    ) {
        if (person == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.PERSON));

        if (cnpj == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.CNPJ));

        this.person = person;
        this.cnpj = cnpj;
    }

    public static LegalPerson create(
            Person person,
            CNPJ cnpj
    ) {
        LegalPerson legalPerson = new LegalPerson(
                person,
                cnpj
        );
        legalPerson.persist();

        return legalPerson;
    }
}
