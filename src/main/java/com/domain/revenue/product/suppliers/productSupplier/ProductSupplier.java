package com.domain.revenue.product.suppliers.productSupplier;

import com.domain.revenue.product.Product;
import com.domain.revenue.supplier.Supplier;
import com.domain.shared.BaseEntity;
import com.infrastructure.exceptions.NotFoundException;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PRODUCT_SUPPLIER")
public class ProductSupplier extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    public Supplier supplier;

    @Column(name = "DISCOUNT_ALLOWED", nullable = false)
    public Boolean discountAllowed;

    protected ProductSupplier() {}

    private ProductSupplier(Product product, Supplier supplier, Boolean discountAllowed) {
        this.product = product;
        this.supplier = supplier;
        this.discountAllowed = discountAllowed;
    }

    public static ProductSupplier create(
            Product product,
            Supplier supplier,
            Boolean discountAllowed) {
        ProductSupplier productSupplier = new ProductSupplier(
                product,
                supplier,
                discountAllowed
        );

        productSupplier.persist();

        return productSupplier;
    }

    public static List<ProductSupplier> getAllProductSuppliers () {
        return ProductSupplier.list(
                "SELECT ps " +
                        "FROM ProductSupplier ps " +
                        "JOIN FETCH ps.product " +
                        "JOIN FETCH ps.supplier s " +
                        "JOIN FETCH s.person"
        );
    }

    public static List<Supplier> getSuppliersOfProduct(Product product) {
        return ProductSupplier.list(
                "SELECT ps.supplier " +
                        "FROM ProductSupplier ps " +
                       "WHERE ps.product.id = ?1",
                product.id
        );
    }



    public static ProductSupplier getByIdOrThrow(Product product, Supplier supplier) {
        return ProductSupplier.<ProductSupplier>find(
                        "product = ?1 " +
                                "and supplier = ?2",
                        product,
                        supplier
                ).firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Produto " + product.id + " para o fornecedor " + supplier.id + " não encontrado."));
    }

    public static ProductSupplier getByIdOrThrow(Long id) {
        return ProductSupplier.<ProductSupplier>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Produte de fornece " + id + "não encontrado."));
    }
}
