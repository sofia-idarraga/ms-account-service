package com.aditya.multimodule.service;

import com.aditya.multimodule.model.Movement;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.model.dto.ReportDTO;
import com.aditya.multimodule.model.dto.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    Result<List<ReportDTO>> generateReport(Long clientNit, LocalDate initDate, LocalDate endDate);
    Result<Movement> createMovement(TransactionDTO transactionDTO);
}
