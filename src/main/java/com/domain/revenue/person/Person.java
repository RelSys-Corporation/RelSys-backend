package com.domain.revenue.person;

import com.domain.shared.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "PERSON_TYPE_ACRONYM",
        discriminatorType = DiscriminatorType.STRING,
        length = 2
)
@DiscriminatorValue("D")
public class Person extends BaseEntity {
    @Column(name = "NAME", length = 200, nullable = false, unique = true)
    public String name;

    @JoinColumn(name = "COMPANY_ID")


    @Column(name = "EMAIL", length = 255, unique = true)
    public String email;

    @Column(name = "PERSON_TYPE_ACRONYM", length = 2, insertable = false, updatable = false)
    public String personTypeAcronym;

    protected Person() {}

    private Person(String name) {
        this.name = name;
    }

    private Person(
            String name,
            String email,
            String personTypeAcronym) {
        this.name = name;
        this.email = email;
        this.personTypeAcronym = personTypeAcronym;
    }
}
