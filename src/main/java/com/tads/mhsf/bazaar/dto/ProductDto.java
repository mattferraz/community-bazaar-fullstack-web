package com.tads.mhsf.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ProductDto {
    private final Integer id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String brand;
    @NotBlank
    private final String category;
    private final String description;
    @Valid
    private final ProductsBatchDto productsBatchDTO;
}
