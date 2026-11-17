package com.domain.commercial.sale.dtos;

import com.domain.commercial.product.Product;
import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductSaleInputDto(
        @RequiredField(entity = Message.Entity.PRODUCT)
        Long productId,
        @RequiredField(entity = Message.Entity.SALE, property = Message.Property.AMOUNT)
        BigDecimal quantity,
        BigDecimal discountPercentage
) {
}
