package com.domain.revenue.person.mappers;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.dtos.PersonOutputDto;
import com.domain.revenue.person.subtype.LegalPerson;
import com.domain.revenue.person.subtype.PhysicalPerson;

public class PersonDtoMapper {
    public static PersonOutputDto toDto(Person entity) {
        return new PersonOutputDto(entity.id);
    }

    public static PersonOutputDto toDto(LegalPerson entity) {
        return new PersonOutputDto(entity.id);
    }

    public static PersonOutputDto toDto(PhysicalPerson entity) {
        return new PersonOutputDto(entity.id);
    }
}
