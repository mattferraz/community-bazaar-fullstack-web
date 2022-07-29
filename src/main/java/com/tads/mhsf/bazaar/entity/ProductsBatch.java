package com.tads.mhsf.bazaar.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductsBatch {
    private int id;
    private String note;
    private LocalDateTime deliveryDate;
    private SupervisoryOrgan supervisoryOrgan;
    private DoneeInstitution doneeInstitution;

    public ProductsBatch(String note,
                         LocalDateTime deliveryDate,
                         SupervisoryOrgan supervisoryOrgan,
                         DoneeInstitution doneeInstitution) {
        this.note = note;
        this.deliveryDate = deliveryDate;
        this.supervisoryOrgan = supervisoryOrgan;
        this.doneeInstitution = doneeInstitution;
    }
}
