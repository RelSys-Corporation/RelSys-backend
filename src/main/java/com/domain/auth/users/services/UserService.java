package com.domain.auth.users.services;

import com.domain.auth.users.dtos.UserCompanyOutputDto;
import com.domain.auth.users.mappers.UserDtoMapper;
import com.domain.auth.users.usersCompany.UsersCompany;
import com.domain.system.company.Company;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {
    public List<UserCompanyOutputDto> getUserAllowedCompanies(Long userId) {
        List<UserCompanyOutputDto> companies = new ArrayList<>();

        for (Company company : UsersCompany.<Company>list("""
                SELECT uc.company
                  FROM UsersCompany uc
                 WHERE uc.user.id = ?1""", userId)) {
            companies.add(UserDtoMapper.toDto(company));
        }

        return companies;
    }
}
