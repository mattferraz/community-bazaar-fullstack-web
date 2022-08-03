package com.tads.mhsf.bazaar.mapper;

import com.tads.mhsf.bazaar.dto.ProductDto;
import com.tads.mhsf.bazaar.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ProductsBatchMapper productsBatchMapper;

    public ProductMapper(ProductsBatchMapper productsBatchMapper) {
        this.productsBatchMapper = productsBatchMapper;
    }

    public Product dtoToEntity(ProductDto productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getBrand(),
                productDTO.getCategory(),
                productDTO.getDescription(),
                productsBatchMapper.dtoToEntity(productDTO.getProductsBatchDTO())
        );
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getDescription(),
                productsBatchMapper.entityToDto(product.getProductsBatch())
        );
    }

}
