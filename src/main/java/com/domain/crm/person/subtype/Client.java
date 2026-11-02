package com.domain.crm.person.subtype;

import com.domain.crm.person.Person;
import com.domain.crm.person.constants.PersonStatus;
import com.domain.shared.BaseEntityCompany;
import jakarta.persistence.*;

@Entity
@Table(name = "CLIENT", schema = "CRM")
public class Client extends BaseEntityCompany {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    public Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_STATUS_ID", nullable = false)
    public PersonStatus personStatus;

    protected Client() {}

    public static Client getById(Long id) {
        if (id == null)
            return null;

        return Client.findById(id);
    }
}
