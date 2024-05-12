package com.aditya.multimodule.repository.enitites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "number", nullable = false)
    private Long number;
    @Column(name = "clientNit", nullable = false)
    private Long clientNit;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "initialBalance", nullable = false)
    private Double initialBalance;
    @Column(name = "balance", nullable = false)
    private Double balance;
    @Column(name = "state", nullable = false)
    private Boolean state;
}
