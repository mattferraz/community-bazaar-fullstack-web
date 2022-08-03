package com.tads.mhsf.bazaar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoneeInstitution {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;
}
