package com.domain.revenue.supplier;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.constant.personStatus.PersonStatus;
import com.domain.shared.BaseEntity;
import com.domain.shared.FindableById;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "SUPPLIER")
public class Supplier extends BaseEntity implements FindableById<Supplier, Long> {
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

    @Override
    public Supplier getByIdOrThrow(Long id) {
        return Supplier.<Supplier>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor " + id + " não encontrado."));
    }
}
