package com.domain.auth.users.usersCompany;

import com.domain.auth.users.Users;
import com.domain.shared.BaseEntity;
import com.domain.system.company.Company;
import com.infrastructure.exceptions.DomainException;
import jakarta.persistence.*;

@Entity
@Table(name = "USERS_COMPANY", schema = "AUTH")
public class UsersCompany extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    Company company;

    protected UsersCompany() {}

    private UsersCompany(
            Users user,
            Company company
    ) {
        if (user == null)
            throw new DomainException("O usuário precissa ser informado.");

        if (company == null)
            throw new DomainException("A empresa precissa ser informado.");

        this.user = user;
        this.company = company;
    }

    static public UsersCompany create(
            Users user,
            Company company
    ) {
        UsersCompany usersCompany = new UsersCompany(
                user,
                company
        );
        usersCompany.persist();

        return usersCompany;
    }
}
