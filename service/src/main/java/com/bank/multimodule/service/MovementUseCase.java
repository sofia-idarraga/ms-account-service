package com.bank.multimodule.service;

import com.bank.multimodule.model.Movement;
import com.bank.multimodule.model.commons.Result;
import com.bank.multimodule.model.dto.ReportDTO;
import com.bank.multimodule.model.dto.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

public interface MovementUseCase extends BaseCrudUseCase<Movement,Long>{


    Result<List<ReportDTO>> generateReport(Long clientNit, LocalDate initDate, LocalDate endDate);

    Result<Movement> createMovement(TransactionDTO transactionDTO);

}
