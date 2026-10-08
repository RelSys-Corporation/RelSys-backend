package com.domain.revenue.client;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.constant.personStatus.PersonStatus;
import com.domain.shared.BaseEntityCompany;
import jakarta.persistence.*;

@Entity
@Table(name = "CLIENT", schema = "REVENUE")
public class Client extends BaseEntityCompany {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    public Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_STATUS_ID", nullable = false)
    public PersonStatus personStatus;

    protected Client() {}
}
