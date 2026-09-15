package com.domain.revenue.supplier.mappers;

import com.domain.revenue.person.Person;
import com.domain.revenue.person.enums.personStatus.PersonStatus;
import com.domain.revenue.supplier.Supplier;
import com.domain.revenue.supplier.dtos.SupplierInputDto;
import com.domain.revenue.supplier.dtos.SupplierOutputDto;

import java.util.Optional;

public class SupplierDtoMapper {
    public static SupplierOutputDto toDto(Supplier entity) {
        return new SupplierOutputDto(entity.id, entity.person.name);
    }

    public static Supplier toEntity(SupplierInputDto dto, Person person) {
        return new Supplier(person, PersonStatus.getActive());
    }
}
