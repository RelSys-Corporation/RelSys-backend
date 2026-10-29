package com.domain.auth.users.mappers;

import com.domain.auth.users.Users;
import com.domain.auth.users.dtos.UserCompanyOutputDto;
import com.domain.auth.users.dtos.UserOutputDto;
import com.domain.system.company.Company;

public class UserDtoMapper {
    public static UserCompanyOutputDto toDto(Company entity) {
        return new UserCompanyOutputDto(entity.id, entity.corporateName);
    }

    public static UserOutputDto toDto(Users user) {
        return new UserOutputDto(user.id, user.name);
    }
}