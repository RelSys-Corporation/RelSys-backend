package com.domain.revenue.product;

import com.domain.revenue.product.suppliers.productSupplier.ProductSupplier;
import com.domain.revenue.supplier.Supplier;
import com.domain.shared.BaseEntity;
import com.domain.shared.valueObjects.barcode.Barcode;
import com.domain.shared.valueObjects.barcode.BarcodeAttributeConverter;
import com.infrastructure.exceptions.NotFoundException;
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
    @Convert(converter = BarcodeAttributeConverter.class)
    public Barcode barcode;

    public Product() {}

    public Product(
            String name,
            BigDecimal price,
            Barcode barcode
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
            Barcode barcode) {

        Supplier supplier = Supplier.getByIdOrThrow(supplierId);

        Product product = new Product(name, price, barcode);
        product.persist();

        ProductSupplier.create(product, supplier, Boolean.FALSE);

        Product.flush();

        return product;
    }

    @PostPersist
    public void postPersist() {
        this.barcode = Barcode.generateBarcode(this.id);
    }

    public static Optional<Product> getProductByBarcode(String barcode) {
        return Product.<Product>find("barcode", barcode)
                .firstResultOptional();
    }

    public static Product getByIdOrThrow(Long id) {
        return Product.<Product>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Produto " + id + " informado não existe."));
    }

}
