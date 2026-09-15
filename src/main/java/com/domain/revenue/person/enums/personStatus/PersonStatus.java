package com.domain.revenue.person.enums.personStatus;

import com.domain.shared.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "PERSON_STATUS")
public class PersonStatus extends BaseEntity {
    @Column(name = "NAME", nullable = false, unique = true)
    public String name;

    public static PersonStatus getActive() {
        return PersonStatus.<PersonStatus>find("id", 1)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("Status 'ATIVO' (ID 1) não encontrado na tabela PERSON_STATUS."));
    }

    public static PersonStatus getDeactivated() {
        return PersonStatus.<PersonStatus>find("id", 2)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("Status 'DESATIVADO' (ID 2) não encontrado na tabela PERSON_STATUS."));
    }
}