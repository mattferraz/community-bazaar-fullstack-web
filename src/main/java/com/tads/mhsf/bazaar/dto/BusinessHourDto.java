package com.tads.mhsf.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class BusinessHourDto {
    private final Integer id;

    @NotNull
    private final Integer weekday;

    @NotNull
    private final LocalTime openTime;

    @NotNull
    private final LocalTime closeTime;

    @NotNull
    @Valid
    private final DoneeInstitutionDto doneeInstitutionDto;
}
