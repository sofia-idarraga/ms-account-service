package com.aditya.multimodule.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Movement {

    private Long id;
    private Long accountNumber;
    private LocalDate date;
    private Type type;
    private Double value;
    private Double initialBalance;

}
