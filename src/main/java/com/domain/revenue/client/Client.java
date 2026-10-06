package com.domain.revenue.client;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.constant.personStatus.PersonStatus;
import com.domain.shared.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "CLIENT")
public class Client extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    public Person person;

    @OneToOne
    @JoinColumn(name = "PERSON_STATUS_ID", nullable = false)
    public PersonStatus personStatus;
}
