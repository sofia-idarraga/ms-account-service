package com.bank.multimodule.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    private Long accountNumber;
    private Double value;

}
