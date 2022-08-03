package com.tads.mhsf.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SupervisoryOrganDto {
    private final Integer id;

    @NotBlank
    private final String name;

    @NotBlank
    private final String description;
}
