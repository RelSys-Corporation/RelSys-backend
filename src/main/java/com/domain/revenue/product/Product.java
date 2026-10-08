package com.domain.revenue.product;

import com.domain.shared.BaseEntityCompany;
import com.domain.shared.valueObjects.barcode.Barcode;
import com.domain.shared.valueObjects.barcode.BarcodeAttributeConverter;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT", schema = "REVENUE")
public class Product extends BaseEntityCompany {
    @Column(name = "NAME", length = 100, nullable = false, unique = true)
    public String name;

    @Column(name = "PRICE", scale = 15, precision = 3, nullable = false)
    public BigDecimal price;

    @Column(name = "BARCODE", length = 50, nullable = false, unique = true)
    @Convert(converter = BarcodeAttributeConverter.class)
    public Barcode barcode;

    @PostPersist
    public void postPersist() {
        this.barcode = Barcode.generateBarcode(this.id);
    }

    protected Product() {}

    private Product(
            String name,
            BigDecimal price,
            Barcode barcode
    ) {
        if (name == null || name.isBlank())
            throw new DomainException("O nome do produto não pode ser nulo.");

        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException("O preço do produto deve ser maior ou igual a 0.");

        this.name = name;
        this.price = price;
        this.barcode = barcode;
    }

    public static Product create(
            String name,
            BigDecimal price,
            Barcode barcode
    ) {
        Product product = new Product(
                name,
                price,
                barcode
        );

        product.persist();

        return product;
    }

    public static Product getProductByBarcode(String barcode) {
        return Product.<Product>find("barcode", barcode)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException(
                        String.format("Nenhum produto encontrado com o código de barras: %s",
                                barcode
                        )
                ));
    }

    public static Product getByIdOrThrow(Long id) {
        return Product.<Product>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Produto " + id + " informado não existe."));
    }

}
