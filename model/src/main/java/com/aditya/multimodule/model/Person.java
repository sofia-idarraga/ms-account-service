package com.aditya.multimodule.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Person {

    private Long nit;
    private String name;
    private Gender gender;
    private String address;
    private Long phoneNumber;
}
