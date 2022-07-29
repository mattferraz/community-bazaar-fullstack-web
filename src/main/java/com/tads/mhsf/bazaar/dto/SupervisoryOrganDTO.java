package com.tads.mhsf.bazaar.dto;

import com.tads.mhsf.bazaar.entity.SupervisoryOrgan;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupervisoryOrganDTO {
    private final int id;
    private final String name;
    private final String description;

    public SupervisoryOrganDTO(SupervisoryOrgan entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
