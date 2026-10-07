package com.domain.revenue.supplier;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.constant.personStatus.PersonStatus;
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

    protected Supplier() {}

    private Supplier(Person person, PersonStatus personStatus) {
        this.person = person;
        this.personStatus = personStatus;
    }

    public static Supplier create(Person person) {
        Supplier supplier = new Supplier(
                person,
                PersonStatus.of(PersonStatus.Type.ACTIVE)
        );
        supplier.persist();

        return supplier;
    }

    public static Supplier getByIdOrThrow(Long id) {
        return Supplier.<Supplier>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor " + id + " não encontrado."));
    }
}
