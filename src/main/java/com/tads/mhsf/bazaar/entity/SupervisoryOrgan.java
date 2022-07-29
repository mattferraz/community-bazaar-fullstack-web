package com.tads.mhsf.bazaar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupervisoryOrgan {
    private int id;
    private String name;
    private String description;

    public SupervisoryOrgan(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
