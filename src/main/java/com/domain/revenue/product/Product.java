package com.domain.revenue.product;

import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.supplier.Supplier;
import com.domain.shared.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal price;

    @Column(name = "BARCODE", length = 50, nullable = false, unique = true)
    /*TODO: Criar tipo*/
    public String barcode;

    public Product() {}

    public Product(
            String name,
            BigDecimal price,
            String barcode
    ) {
        this.name = name;
        this.price = price;
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Product create(
            String name,
            Long supplierId,
            BigDecimal price,
            String barcode) {

        Supplier supplier = Supplier.getSupplierById(supplierId);

        Product product = new Product(name, price, barcode);
        product.persist();

        ProductSupplier.create(product, supplier, Boolean.FALSE);

        Product.flush();

        return product;
    }

    @PostPersist
    public void generateBarcodeIfNull() {
        if (this.barcode == null || this.barcode.isBlank()) {
            this.barcode = String.format("%013d", this.id);
        }
    }

    public static Optional<Product> getProductByBarcode(String barcode) {
        return Product.<Product>find("barcode", barcode)
                .firstResultOptional();
    }

}
