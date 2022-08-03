package com.tads.mhsf.bazaar.service;

import com.tads.mhsf.bazaar.dto.ProductDto;
import com.tads.mhsf.bazaar.entity.Product;
import com.tads.mhsf.bazaar.exception.DataNotFoundException;
import com.tads.mhsf.bazaar.mapper.ProductMapper;
import com.tads.mhsf.bazaar.repository.ProductRepository;
import com.tads.mhsf.bazaar.repository.ProductsBatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductsBatchRepository productsBatchRepository;

    public ProductService(ProductMapper productMapper,
                          ProductRepository productRepository,
                          ProductsBatchRepository productsBatchRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productsBatchRepository = productsBatchRepository;
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Integer productsBatchId = productDto.getProductsBatchDTO().getId();
        if (productsBatchRepository.findById(productsBatchId).isPresent()) {
            Product product = productMapper.dtoToEntity(productDto);
            return productMapper.entityToDto(productRepository.save(product));
        } else {
            throw new DataNotFoundException("Product batch not found with id: " + productsBatchId);
        }
    }

    @Transactional
    public ProductDto findProductDtoById(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + id));

        return productMapper.entityToDto(product);
    }

    @Transactional
    public List<ProductDto> findAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::entityToDto)
                .toList();
    }

    @Transactional
    public void updateProduct(Integer id, ProductDto newProductDto) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + id));

        Integer productsBatchId = newProductDto.getProductsBatchDTO().getId();
        if (productsBatchRepository.findById(productsBatchId).isPresent()) {
            Product newProduct = productMapper.dtoToEntity(newProductDto);
            product.setId(id);
            productRepository.update(product);
        } else {
            throw new DataNotFoundException("Products batch not found with id: " + productsBatchId);
        }
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Product not found with id: " + id);
        }
    }
}
