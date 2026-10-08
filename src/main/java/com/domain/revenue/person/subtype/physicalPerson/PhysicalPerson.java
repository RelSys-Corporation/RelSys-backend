package com.domain.revenue.person.subtype.physicalPerson;

import com.domain.revenue.person.Person;
import com.domain.shared.valueObjects.cpf.CPF;
import com.domain.shared.valueObjects.cpf.CpfAttributeConverter;
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
}
