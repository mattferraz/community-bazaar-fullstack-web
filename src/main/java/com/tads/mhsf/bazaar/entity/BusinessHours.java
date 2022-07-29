package com.tads.mhsf.bazaar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessHours {
    private int id;
    private int weekday;
    private int openTime;
    private int closeTime;
    private DoneeInstitution doneeInstitution;

    public BusinessHours(int weekday,
                         int openTime,
                         int closeTime,
                         DoneeInstitution doneeInstitution) {
        this.weekday = weekday;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.doneeInstitution = doneeInstitution;
    }
}
