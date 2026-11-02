package com.domain.commercial.sale.mappers;

import com.domain.commercial.sale.Sale;
import com.domain.commercial.sale.dtos.SaleOutputDto;

public class SaleDtoMapper {
    public static SaleOutputDto toDto(Sale sale) {
        return new SaleOutputDto(
                sale.id,
                sale.client.person.name,
                sale.paymentMethod.name,
                sale.createdAt
        );
    }
}
