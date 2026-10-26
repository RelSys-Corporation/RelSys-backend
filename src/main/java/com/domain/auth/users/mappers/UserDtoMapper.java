package com.domain.auth.users.mappers;

import com.domain.auth.users.dtos.UserCompanyOutputDto;
import com.domain.system.company.Company;

public class UserDtoMapper {
    public static UserCompanyOutputDto toDto(Company entity) {
        return new UserCompanyOutputDto(entity.id, entity.corporateName);
    }
}