package com.tads.mhsf.bazaar.controller;

import com.tads.mhsf.bazaar.dto.ProductsBatchDto;
import com.tads.mhsf.bazaar.service.ProductsBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/products-batches")
public class ProductsBatchController {

    private final ProductsBatchService productsBatchService;

    public ProductsBatchController(ProductsBatchService productsBatchService) {
        this.productsBatchService = productsBatchService;
    }

    @PostMapping
    public ResponseEntity<ProductsBatchDto> createProductsBatch(@RequestBody @Valid
                                                                ProductsBatchDto productsBatchDto,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        final String url = "api/v1/products-batches/{id}";
        ProductsBatchDto createdDto = productsBatchService.createProductsBatch(productsBatchDto);
        URI createdUri = uriComponentsBuilder.path(url).buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(createdUri).body(createdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsBatchDto> findProductsBatchById(@PathVariable Integer id) {
        return ResponseEntity.ok(productsBatchService.findProductsBatchById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductsBatchDto>> findAllProductsBatches() {
        return ResponseEntity.ok(productsBatchService.findAllProductsBatch());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductsBatch(@PathVariable Integer id,
                                                    @RequestBody @Valid
                                                    ProductsBatchDto productsBatchDto) {
        productsBatchService.updateProductsBatch(id, productsBatchDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductsBatches(@PathVariable Integer id) {
        productsBatchService.deleteProductsBatchById(id);
        return ResponseEntity.noContent().build();
    }
}
