package com.domain.revenue.product.suppliers.productSupplier.services;

import com.domain.revenue.product.Product;
import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.supplier.Supplier;
import com.domain.shared.valueObjects.barcode.Barcode;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductSupplierService {
    public static Product createProduct(
            String productName,
            BigDecimal productPrice,
            String productBarcode,
            Long supplierId
    ) {
        Supplier supplier = Supplier.getByIdOrThrow(supplierId);

        Product product = Product.create(
                productName,
                productPrice,
                new Barcode(productBarcode)
        );

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

    public static List<Supplier> getSuppliersOfProduct(Product product) {
        return ProductSupplier.list(
                "SELECT ps.supplier " +
                        "FROM ProductSupplier ps " +
                        "WHERE ps.product = ?1",
                product
        );
    }

    public static ProductSupplier getByIdOrThrow(Product product, Supplier supplier) {
        return ProductSupplier.<ProductSupplier>find(
                        "product = ?1 " +
                                "and supplier = ?2",
                        product,
                        supplier
                ).firstResultOptional()
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                "Produto %s (%d) para o fornecedor %s (%d) não encontrado.",
                                product.name,
                                product.id,
                                supplier.person.name,
                                supplier.id
                        )
                ));
    }
}
