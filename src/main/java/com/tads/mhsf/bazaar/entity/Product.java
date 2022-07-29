package com.tads.mhsf.bazaar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String brand;
    private String description;
    private ProductsBatch productsBatch;

    public Product(String name,
                   String brand,
                   String description,
                   ProductsBatch productsBatch) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.productsBatch = productsBatch;
    }
}
