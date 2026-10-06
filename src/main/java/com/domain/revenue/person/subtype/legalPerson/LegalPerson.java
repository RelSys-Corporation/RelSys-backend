package com.domain.revenue.person.subtype.legalPerson;

import com.domain.revenue.person.Person;
import com.domain.shared.valueObjects.cnpj.CNPJ;
import com.domain.shared.valueObjects.cnpj.CnpjAttributeConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "LEGAL_PERSON")
@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
@DiscriminatorValue("J")
public class LegalPerson extends Person {
    /*TODO: Criar tipo*/
    @Column(name = "CNPJ", length = 14, nullable = false, unique = true)
    @Convert(converter = CnpjAttributeConverter.class)
    public CNPJ cnpj;
}
