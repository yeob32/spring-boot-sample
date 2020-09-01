package com.example.batch.domain.product;

import com.example.batch.domain.product.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus status;

    public static Product createProduct(String name, ProductStatus productStatus) {
        Product product = new Product();
        product.name = name;
        product.status = productStatus;

        return product;
    }
}
