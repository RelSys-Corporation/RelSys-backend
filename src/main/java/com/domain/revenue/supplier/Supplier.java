package com.domain.revenue.supplier;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.enums.personStatus.PersonStatus;
import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "SUPPLIER")
public class Supplier extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    public Person person;

    @OneToOne
    @JoinColumn(name = "PERSON_STATUS_ID", nullable = false)
    public PersonStatus personStatus;

    public Supplier() {}

    public Supplier(Person person, PersonStatus personStatus) {
        this.person = person;
        this.personStatus = personStatus;
    }

    public static Supplier create(String name) {
        Person person = new Person(name);
        person.persist();

        Supplier supplier = new Supplier(
                person,
                PersonStatus.getActive()
        );
        supplier.persist();

        return supplier;
    }

    public static Supplier findByIdTreated(Long id) {
        return Supplier.<Supplier>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor " + id + " não encontrado."));
    }
}
