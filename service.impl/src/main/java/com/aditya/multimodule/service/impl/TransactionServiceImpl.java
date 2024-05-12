package com.aditya.multimodule.service.impl;

import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.Client;
import com.aditya.multimodule.model.Movement;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.model.dto.ReportDTO;
import com.aditya.multimodule.model.dto.TransactionDTO;
import com.aditya.multimodule.repository.adapters.AccountAdapter;
import com.aditya.multimodule.repository.adapters.MovementAdapter;
import com.aditya.multimodule.rest.RestClientAdapter;
import com.aditya.multimodule.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final RestClientAdapter clientAdapter;
    private final AccountAdapter accountAdapter;
    private final MovementAdapter movementAdapter;

    public TransactionServiceImpl(RestClientAdapter clientAdapter, AccountAdapter accountAdapter, MovementAdapter movementAdapter) {
        this.clientAdapter = clientAdapter;
        this.accountAdapter = accountAdapter;
        this.movementAdapter = movementAdapter;
    }

    @Override
    public Result<Movement> createMovement(TransactionDTO transactionDTO) {
        Result<Account> accountResult = accountAdapter.findById(transactionDTO.getAccountNumber());
        if (isInvalidAccount(accountResult)) {
            return Result.errorResult(ErrorCode.NOT_FOUND,
                    "Error creating Movement due to given account is invalid in this moment");
        }

        Account foundAccount = accountResult.getValue();

        Movement movement = new Movement();
        Double movementValue = transactionDTO.getValue();

        movement.setValue(movementValue);
        movement.setMovementType(movementValue);
        movement.setInitialBalance(foundAccount.getBalance());
        movement.setAccount(foundAccount);

        foundAccount.setNewBalance(movementValue);

        if(isInvalidMovement(transactionDTO, foundAccount)){
            return Result.errorResult(ErrorCode.CLIENT_ERROR,
                    "Insufficient balance");
        }
        return saveMovement(foundAccount, movement, movementValue);
    }

    @Override
    public Result<List<ReportDTO>> generateReport(Long clientNit, LocalDate initDate, LocalDate endDate) {
        Result<Client> clientResult = clientAdapter.getClient(clientNit.toString());
        if (!clientResult.isSuccess()) {
            return Result.errorResult(ErrorCode.SERVER_ERROR,
                    "Error generating report due to given client nit could not be found");
        }
        Result<List<Movement>> movementsResult = movementAdapter.findByAccountClientNitAndDateBetween(clientNit, initDate, endDate);

        if (!movementsResult.isSuccess()) {
            return Result.errorResult(ErrorCode.SERVER_ERROR,
                    "Error generating report due to movements could not be found");
        }
        List<ReportDTO> reports = movementsResult.getValue()
                .stream()
                .map(movement -> mapToReport(movement, clientResult.getValue().getName()))
                .collect(Collectors.toList());
        return new Result<>(reports);
    }

    private Result<Movement> saveMovement(Account foundAccount, Movement movement, Double movementValue) {
        Result<Account> updatedAccount = accountAdapter.save(foundAccount);
        if (!updatedAccount.isSuccess()) {
            return Result.errorResult(ErrorCode.SERVER_ERROR,
                    "Error creating Movement due to account could not be updated");
        }

        Result<Movement> movementResult = movementAdapter.save(movement);
        if (!movementResult.isSuccess()) {
            foundAccount.reversBalance(movementValue);
            accountAdapter.save(foundAccount);
            return Result.errorResult(ErrorCode.SERVER_ERROR,
                    "Error creating Movement due to movement could not be saved");
        }
        return movementResult;
    }

    private static boolean isInvalidAccount(Result<Account> accountResult) {
        return !accountResult.isSuccess() || Boolean.FALSE.equals(accountResult.getValue().getState());
    }

    private static boolean isInvalidMovement(TransactionDTO transaction, Account account){
        return transaction.getValue().equals(0.0) || (account.getBalance() < 0);
    }


    private ReportDTO mapToReport(Movement movement, String clientName) {
        ReportDTO report = new ReportDTO();
        report.setDate(movement.getDate());
        report.setClientName(clientName);
        report.setNumber(movement.getAccount().getNumber());
        report.setState(movement.getAccount().getState());
        report.setAccountType(movement.getAccount().getType());
        report.setMovementType(movement.getType());
        report.setInitialBalance(movement.getInitialBalance());
        report.setValue(movement.getValue());
        report.setFinalBalance();
        return report;
    }
}
