package com.domain.revenue.product.suppliers.productSupplier.services;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.supplier.Supplier;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductSupplierService {
    public static Product createProduct(
            Product product,
            Long supplierId
    ) {

        Supplier supplier = Supplier.findByIdTreated(supplierId);

        product.persist();

        ProductSupplier.create(product, supplier, Boolean.FALSE);

        Product.flush();

        return product;
    }

    public static Map<Product, List<Supplier>> getAllProductAndSuppliers() {
        List<ProductSupplier> productSuppliers = ProductSupplier.getAllProductSuppliers();

        return productSuppliers.stream()
                .collect(Collectors.groupingBy(
                        ps -> ps.product,
                        Collectors.mapping(ps -> ps.supplier, Collectors.toList())
                ));
    }
}
