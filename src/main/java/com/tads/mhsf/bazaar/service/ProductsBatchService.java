package com.tads.mhsf.bazaar.service;

import com.tads.mhsf.bazaar.dto.ProductDto;
import com.tads.mhsf.bazaar.dto.ProductsBatchDto;
import com.tads.mhsf.bazaar.entity.ProductsBatch;
import com.tads.mhsf.bazaar.exception.DataNotFoundException;
import com.tads.mhsf.bazaar.exception.UnprocessableRequestException;
import com.tads.mhsf.bazaar.mapper.ProductMapper;
import com.tads.mhsf.bazaar.mapper.ProductsBatchMapper;
import com.tads.mhsf.bazaar.repository.ProductRepository;
import com.tads.mhsf.bazaar.repository.ProductsBatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProductsBatchService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductsBatchMapper productsBatchMapper;
    private final ProductsBatchRepository productsBatchRepository;

    public ProductsBatchService(ProductMapper productMapper,
                                ProductRepository productRepository,
                                ProductsBatchMapper productsBatchMapper,
                                ProductsBatchRepository productsBatchRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productsBatchMapper = productsBatchMapper;
        this.productsBatchRepository = productsBatchRepository;
    }

    @Transactional
    public ProductsBatchDto createProductsBatch(ProductsBatchDto productsBatchDto) {
        ProductsBatch productsBatch = productsBatchMapper.dtoToEntity(productsBatchDto);
        return productsBatchMapper.entityToDto(productsBatchRepository.save(productsBatch));
    }

    @Transactional
    public ProductsBatchDto findProductsBatchById(Integer id) {
        ProductsBatch productsBatch = productsBatchRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Products batch not found with id: " + id));

        return productsBatchMapper.entityToDto(productsBatch);
    }

    @Transactional
    public List<ProductsBatchDto> findAllProductsBatch() {
        return productsBatchRepository
                .findAll()
                .stream()
                .map(productsBatchMapper::entityToDto)
                .toList();
    }

    @Transactional
    public List<ProductDto> findAllProductsFromProductBatchById(Integer id) {
        return productRepository
                .findAllProductsFromBatchById(id)
                .stream()
                .map(productMapper::entityToDto)
                .toList();
    }

    @Transactional
    public void updateProductsBatch(Integer id, ProductsBatchDto productsBatchDto) {
        if (productsBatchRepository.findById(id).isPresent()) {
            ProductsBatch productsBatch = productsBatchMapper.dtoToEntity(productsBatchDto);
            productsBatch.setId(id);
            productsBatchRepository.update(productsBatch);
        } else {
            throw new DataNotFoundException("Products batch not found with id: " + id);
        }
    }

    @Transactional
    public void deleteProductsBatchById(Integer id) {
        ProductsBatch productsBatch = productsBatchRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Products batch not found with id: " + id));

        long minutesElapsedSinceProductBatchCreation = ChronoUnit.MINUTES.between(productsBatch.getDeliveryDate(), LocalDateTime.now());
        if (minutesElapsedSinceProductBatchCreation < 30) {
            productRepository.deleteAllProductsFromProductsBatchId(id);
            productsBatchRepository.deleteById(productsBatch.getId());
        } else {
            throw new UnprocessableRequestException("Products batch cannot be deleted if 30 minutes or more have " +
                    "been passed since it was created. Minutes elapsed: " + minutesElapsedSinceProductBatchCreation);
        }
    }
}
