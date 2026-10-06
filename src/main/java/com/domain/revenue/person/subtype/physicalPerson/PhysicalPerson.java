package com.domain.revenue.person.subtype.physicalPerson;

import com.domain.revenue.person.Person;
import jakarta.persistence.*;

@Entity
@Table(name = "PHYSICAL_PERSON")
@PrimaryKeyJoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
@DiscriminatorValue("F")
public class physicalPerson extends Person {
    /*TODO: Criar tipo*/
    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    public String cpf;
}
