package com.domain.revenue.person.subtype.legalPerson;

import com.domain.revenue.person.Person;
import com.domain.shared.valueObjects.cnpj.CNPJ;
import com.domain.shared.valueObjects.cnpj.CnpjAttributeConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "LEGAL_PERSON", schema = "REVENUE")
@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
@DiscriminatorValue("L")
public class LegalPerson extends Person {
    @Convert(converter = CnpjAttributeConverter.class)
    @Column(name = "CNPJ", length = 14, nullable = false, unique = true)
    public CNPJ cnpj;

    protected LegalPerson() {}
}
