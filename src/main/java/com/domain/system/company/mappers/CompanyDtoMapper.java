package com.domain.system.company.mappers;

import com.domain.system.company.Company;
import com.domain.system.company.dtos.CompanyOutputDto;

public class CompanyDtoMapper {
    public static CompanyOutputDto toDto(Company entity) {
        return new CompanyOutputDto(
                entity.id
        );
    }
}
