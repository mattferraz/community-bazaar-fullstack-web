package com.tads.mhsf.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductsBatchDto {
    private final Integer id;
    private final String note;
    @NotBlank
    private final LocalDateTime deliveryDate;
    @NotNull
    private final SupervisoryOrganDto supervisoryOrganDTO;
    private final DoneeInstitutionDto doneeInstitutionDTO;
}
