package com.domain.revenue.person.subtype;

import com.domain.revenue.person.Person;
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
@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
@DiscriminatorValue("L")
public class LegalPerson extends Person {
    @Convert(converter = CnpjAttributeConverter.class)
    @Column(name = "CNPJ", length = 14, nullable = false, unique = true)
    public CNPJ cnpj;

    protected LegalPerson() {}

    protected LegalPerson(
            String name,
            String email,
            CNPJ cnpj
    ) {
        super(name, email);

        if (cnpj == null)
            throw new DomainException("O CNPJ deve ser informado.");

        this.cnpj = cnpj;
    }

    public static LegalPerson create(
            String name,
            String email,
            CNPJ cnpj
    ) {
        LegalPerson person = new LegalPerson(
                name,
                email,
                cnpj
        );

        person.companyId = person.getCompanyId();

        person.persist();

        return person;
    }
}
