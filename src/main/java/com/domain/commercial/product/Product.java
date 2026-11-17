package com.domain.commercial.product;

import com.domain.shared.BaseEntityCompany;
import com.domain.shared.valueObjects.barcode.Barcode;
import com.domain.shared.valueObjects.barcode.BarcodeAttributeConverter;
import com.domain.system.message.Message;
import com.infrastructure.exceptions.DomainException;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT", schema = "COMMERCIAL")
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
            throw new DomainException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.PRODUCT, Message.Property.NAME));

        // TODO - Review to put the message in Database
        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new DomainException(Message.format(Message.Type.SHOULD_HAVE, Message.Entity.PRODUCT, Message.Property.PRICE, "maior ou igual a 0."));

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

    public static Product getProductByBarcode(Barcode barcode) {
        if (barcode == null || barcode.toString().isBlank())
            throw new DomainException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.PRODUCT));

        return Product.<Product>find("barcode", barcode)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.PRODUCT, barcode)));
    }

    public static Product getByIdOrThrow(Long id) {
        if (id == null)
            throw new DomainException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.PRODUCT));

        return Product.<Product>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(Message.format(Message.Type.NOT_FOUND, Message.Entity.PRODUCT, id)));
    }

}
