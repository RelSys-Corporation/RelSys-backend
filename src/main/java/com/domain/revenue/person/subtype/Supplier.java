package com.domain.revenue.person.subtype;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.constants.PersonStatus;
import com.domain.shared.BaseEntityCompany;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

@Entity
@Table(name = "SUPPLIER", schema = "REVENUE")
public class Supplier extends BaseEntityCompany {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    public Person person;

    @ManyToOne(fetch = FetchType.LAZY)
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
        if (id == null)
            throw new DomainException("O identificador do fornecedor não foi informado.");

        return Supplier.<Supplier>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor (" + id + ") não encontrado."));
    }
}
