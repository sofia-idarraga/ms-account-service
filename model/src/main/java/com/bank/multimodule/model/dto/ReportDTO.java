package com.bank.multimodule.model.dto;

import com.bank.multimodule.model.MovementType;
import com.bank.multimodule.model.Type;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportDTO {

    private LocalDate date;
    private String clientName;
    private Long number;
    private Boolean state;
    private Type accountType;
    private MovementType movementType;
    private Double initialBalance;
    private Double value;
    private Double finalBalance;

    public void setFinalBalance(){
        this.finalBalance = initialBalance+value;
    }
}
