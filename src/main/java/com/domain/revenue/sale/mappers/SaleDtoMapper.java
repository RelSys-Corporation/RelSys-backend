package com.domain.revenue.sale.mappers;

import com.domain.revenue.sale.Sale;
import com.domain.revenue.sale.dtos.SaleOutputDto;

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
