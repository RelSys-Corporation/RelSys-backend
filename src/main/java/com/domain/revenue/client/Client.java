package com.domain.revenue.client;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.enums.personStatus.PersonStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT")
public class Client extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    public Person person;

    @OneToOne
    @JoinColumn(name = "PERSON_STATUS_ID", nullable = false)
    public PersonStatus personStatus;

    @Column(name = "CREATED_AT", nullable = false)
    public LocalDateTime createdAt;
}
