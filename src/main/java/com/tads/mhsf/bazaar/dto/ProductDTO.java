package com.tads.mhsf.bazaar.dto;

import com.tads.mhsf.bazaar.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private final int id;
    private final String name;
    private final String brand;
    private final String description;
    private final ProductsBatchDTO productsBatchDTO;

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.brand = entity.getBrand();
        this.description = entity.getDescription();
        this.productsBatchDTO = new ProductsBatchDTO(entity.getProductsBatch());
    }
}
