package com.bank.multimodule.service.impl;

import com.bank.multimodule.model.Movement;
import com.bank.multimodule.model.commons.Result;
import com.bank.multimodule.model.dto.ReportDTO;
import com.bank.multimodule.model.dto.TransactionDTO;
import com.bank.multimodule.repository.adapters.MovementAdapter;
import com.bank.multimodule.service.MovementUseCase;
import com.bank.multimodule.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovementUseCaseImpl implements MovementUseCase {
    private final MovementAdapter movementAdapter;
    private final TransactionService transactionService;

    public MovementUseCaseImpl(MovementAdapter movementAdapter, TransactionService transactionService) {
        this.movementAdapter = movementAdapter;
        this.transactionService = transactionService;
    }

    @Override
    public Result<Movement> createMovement(TransactionDTO transactionDTO) {
        return transactionService.createMovement(transactionDTO);
    }

    @Override
    public Result<List<ReportDTO>> generateReport(Long clientNit, LocalDate initDate, LocalDate endDate) {
        return transactionService.generateReport(clientNit, initDate, endDate);
    }

    @Override
    public Result<Movement> findById(Long id) {
        return movementAdapter.findById(id);
    }

    @Override
    public Result<List<Movement>> findAll(String direction) {
        return movementAdapter.findAll(direction);
    }

    @Override
    public Result<List<Movement>> findAll(int pageNumber, int pageSize, String sortDirection) {
        return movementAdapter.findAll(pageNumber, pageSize, sortDirection);
    }

    @Override
    public Result<Boolean> deleteById(Long id) {
        return movementAdapter.deleteById(id);
    }


}
