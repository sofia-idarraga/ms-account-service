package com.aditya.multimodule.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.aditya.multimodule.model.MovementType.DEPOSITO;
import static com.aditya.multimodule.model.MovementType.RETIRO;

@Getter
@Setter
public class Movement {

    private Long id;
    private LocalDate date;
    private MovementType type;
    private Double value;
    private Double initialBalance;
    private Account account;

    public void setMovementType(Double inputValue) {
        if (inputValue < 0) {
            this.type = RETIRO;
        } else {
            this.type = DEPOSITO;
        }
    }

}
