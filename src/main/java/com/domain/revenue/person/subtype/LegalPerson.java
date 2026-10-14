package com.domain.revenue.person.subtype;

import com.domain.revenue.person.Person;
import com.domain.shared.BaseEntityCompany;
import com.domain.shared.valueObjects.cnpj.CNPJ;
import com.domain.shared.valueObjects.cnpj.CnpjAttributeConverter;
import com.domain.system.company.Company;
import com.domain.system.company.CompanyIdAttributeConverter;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.TenantId;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "LEGAL_PERSON", schema = "REVENUE")
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
            throw new DomainException("A pessoa base deve ser informada.");

        if (cnpj == null)
            throw new DomainException("O CNPJ deve ser informado.");

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
