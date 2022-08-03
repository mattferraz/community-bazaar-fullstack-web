package com.tads.mhsf.bazaar.controller;

import com.tads.mhsf.bazaar.dto.ProductDto;
import com.tads.mhsf.bazaar.service.ProductService;
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
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        final String url = "api/v1/products/{id}";
        ProductDto createdDto = productService.createProduct(productDto);
        URI createdUri = uriComponentsBuilder.path(url).buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(createdUri).body(createdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findProductDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Integer id,
                                              @RequestBody @Valid ProductDto productDto) {
        productService.updateProduct(id, productDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
