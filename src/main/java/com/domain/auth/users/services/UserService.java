package com.domain.auth.users.services;

import com.domain.auth.users.dtos.UserCompanyOutputDto;
import com.domain.auth.users.mappers.UserDtoMapper;
import com.domain.system.company.Company;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {
    public List<UserCompanyOutputDto> getUserAllowedCompanies(Long userId) {
        // TODO update to new table
        List<UserCompanyOutputDto> companies = new ArrayList<>();

        for (Company company : Company.<Company>findAll().list()) {
            companies.add(UserDtoMapper.toDto(company));
        }

        return companies;
    }
}
