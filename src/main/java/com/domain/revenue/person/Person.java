package com.domain.revenue.person;

import com.domain.shared.BaseEntityCompany;
import jakarta.persistence.*;

@Entity
@Table(name = "PERSON", schema = "REVENUE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "PERSON_TYPE_ACRONYM",
        discriminatorType = DiscriminatorType.STRING,
        length = 2
)
@DiscriminatorValue("D")
public class Person extends BaseEntityCompany {
    @Column(name = "NAME", length = 200, nullable = false)
    public String name;

    @Column(name = "EMAIL", length = 255)
    public String email;

    @Column(name = "PERSON_TYPE_ACRONYM", length = 2, insertable = false, updatable = false)
    public String personTypeAcronym;

    protected Person() {}
}
