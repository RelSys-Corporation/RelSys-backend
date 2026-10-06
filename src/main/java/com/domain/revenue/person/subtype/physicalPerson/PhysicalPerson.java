package com.domain.revenue.person.subtype.physicalPerson;

import com.domain.revenue.person.Person;
import com.domain.shared.valueObjects.cpf.CPF;
import com.domain.shared.valueObjects.cpf.CpfAttributeConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "PHYSICAL_PERSON")
@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
@DiscriminatorValue("F")
public class PhysicalPerson extends Person {
    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    @Convert(converter = CpfAttributeConverter.class)
    public CPF cpf;
}
