package com.tads.mhsf.bazaar.dto;

import com.tads.mhsf.bazaar.entity.DoneeInstitution;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoneeInstitutionDTO {
    private final int id;
    private final String name;
    private final String address;
    private final String phoneNumber;
    private final String description;

    public DoneeInstitutionDTO(DoneeInstitution entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.phoneNumber = entity.getPhoneNumber();
        this.description = entity.getDescription();
    }
}
