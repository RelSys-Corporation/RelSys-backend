package com.domain.revenue.product.mappers;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.dtos.ProductInputDto;
import com.domain.revenue.product.dtos.ProductOutputDto;
import com.domain.revenue.supplier.Supplier;
import com.domain.revenue.supplier.mappers.SupplierDtoMapper;

import java.util.List;

public class ProductDtoMapper {
    /*public static Product toEntity(ProductInputDto dto) {
        return new Product(
                dto.name(),
                dto.supplierId(),
                dto.price(),
                dto.barcode()
        );
    }
*/
    public static ProductOutputDto toDto (Product entity, List<Supplier> suppliers) {
        return new ProductOutputDto(
                entity.id,
                suppliers.stream()
                        .map(SupplierDtoMapper::toDto)
                        .toList(),
                entity.name,
                entity.price,
                entity.barcode);
    }
}
