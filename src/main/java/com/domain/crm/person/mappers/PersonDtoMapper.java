package com.domain.crm.person.mappers;

import com.domain.crm.person.Person;
import com.domain.crm.person.dtos.PersonOutputDto;
import com.domain.crm.person.subtype.LegalPerson;
import com.domain.crm.person.subtype.PhysicalPerson;

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
