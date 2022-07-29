package com.tads.mhsf.bazaar.dto;

import com.tads.mhsf.bazaar.entity.ProductsBatch;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductsBatchDTO {
    private final int id;
    private final String note;
    private final LocalDateTime deliveryDate;
    private final SupervisoryOrganDTO supervisoryOrganDTO;
    private final DoneeInstitutionDTO doneeInstitutionDTO;

    public ProductsBatchDTO(ProductsBatch entity) {
        this.id = entity.getId();
        this.note = entity.getNote();
        this.deliveryDate = entity.getDeliveryDate();
        this.supervisoryOrganDTO = new SupervisoryOrganDTO(entity.getSupervisoryOrgan());
        this.doneeInstitutionDTO = new DoneeInstitutionDTO(entity.getDoneeInstitution());
    }
}
