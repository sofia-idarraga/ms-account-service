package com.aditya.multimodule.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private Long number;
    private Long clientNit;
    private Type type;
    private Double initialBalance;
    private Double balance;
    private Boolean state;
}