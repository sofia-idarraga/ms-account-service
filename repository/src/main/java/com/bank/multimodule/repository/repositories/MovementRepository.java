package com.bank.multimodule.repository.repositories;

import com.bank.multimodule.repository.enitites.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Long> {

    List<MovementEntity> findByAccountNumber(Long accountNumber);

    List<MovementEntity> findByAccountClientNit(Long clientNit);
    List<MovementEntity> findByAccountClientNitAndDateBetween(Long clientNit, LocalDate startDate, LocalDate endDate);

}
