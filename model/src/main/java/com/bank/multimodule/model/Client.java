package com.bank.multimodule.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client extends Person {

    private String password;
    private Boolean state;
}
