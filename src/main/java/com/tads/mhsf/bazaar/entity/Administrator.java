package com.tads.mhsf.bazaar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Administrator {
    private int id;
    private String name;
    private String email;
    private String password;
}
