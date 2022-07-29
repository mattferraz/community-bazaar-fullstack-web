package com.tads.mhsf.bazaar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoneeInstitution {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;

    public DoneeInstitution(String name,
                            String address,
                            String phoneNumber,
                            String description) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }
}
