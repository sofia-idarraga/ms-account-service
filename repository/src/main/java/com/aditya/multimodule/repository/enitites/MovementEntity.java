package com.aditya.multimodule.repository.enitites;

import com.aditya.multimodule.model.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
@Entity
@Getter
@Setter
@Table(name = "movement")
public class MovementEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "accountNumber", nullable = false)
    private Long accountNumber;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "type", nullable = false)
    private Type type;
    @Column(name = "value", nullable = false)
    private Double value;
    @Column(name = "initialBalance", nullable = false)
    private Double initialBalance;
}
