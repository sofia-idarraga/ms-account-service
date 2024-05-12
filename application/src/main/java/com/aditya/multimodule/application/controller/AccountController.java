package com.aditya.multimodule.application.controller;


import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.service.AccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aditya.multimodule.model.commons.ErrorCode.GENERIC_ERROR;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController {

    private final AccountUseCase accountUseCase;

    public AccountController(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }


    @PostMapping("/nuevo")
    public ResponseEntity<Result<Account>> saveAccount(@RequestBody Account client) {
        return processResult(accountUseCase.createAccount(client));
    }

    @GetMapping("/{nit}")
    public ResponseEntity<Result<Account>> findAccountByNumber(@PathVariable Long nit) {
        Result<Account> result = accountUseCase.findAccountByNumber(nit);
        return processResult(result);
    }

    @GetMapping("/todos")
    public ResponseEntity<Result<List<Account>>> findAllAccounts(@RequestParam(defaultValue = "ASC") String direction) {
        String sort = "ASC";
        if (direction.equals("ASC") || direction.equals("DESC")) {
            sort = direction;
        }
        Result<List<Account>> result = accountUseCase.findAllAccounts(sort);
        return processResult(result);
    }

    @GetMapping("/todos/page")
    public ResponseEntity<Result<List<Account>>> findAllAccountsPage(@RequestParam(defaultValue = "1") int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                                   @RequestParam(defaultValue = "ASC") String direction) {
        String sort = "ASC";
        if (direction.equals("ASC") || direction.equals("DESC")) {
            sort = direction;
        }
        Result<List<Account>> result = accountUseCase.findAllAccounts(pageNumber, pageSize, sort);
        return processResult(result);
    }

    @PutMapping("/{number}")
    public ResponseEntity<Result<Account>> updateClientInformation(@PathVariable Long number, @RequestBody Account account) {
        account.setNumber(number);
        Result<Account> result = accountUseCase.updateAccountInformation(account);
        return processResult(result);
    }

    @PutMapping("/desactivar/{nit}")
    public ResponseEntity<Result<Boolean>> deactivateClient(@PathVariable String nit) {
        Result<Boolean> result = accountUseCase.deactivateAccounts(Long.parseLong(nit));
        return processResult(result);
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<Result<Boolean>> deleteClientByNit(@PathVariable Long nit) {
        Result<Boolean> result = accountUseCase.deleteAccountByNumber(nit);
        return processResult(result);
    }

    private <T> ResponseEntity<Result<T>> processResult(Result<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            ErrorCode errorCode = result.getErrors().get(0).getCode();
            return switch (errorCode) {
                case SERVER_ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
                case CLIENT_ERROR -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
                case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
                case GENERIC_ERROR -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
            };
        }
    }
}