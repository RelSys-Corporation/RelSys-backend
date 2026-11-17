package com.domain.auth.users.usersCompany;

import com.domain.auth.users.Users;
import com.domain.shared.BaseEntity;
import com.domain.system.company.Company;
import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
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
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.USER));

        if (company == null)
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.COMPANY));

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
