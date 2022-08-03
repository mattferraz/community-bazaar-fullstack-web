package com.tads.mhsf.bazaar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusinessHour {
    private Integer id;
    private int weekday;
    private int openTime;
    private int closeTime;
    private DoneeInstitution doneeInstitution;
}
