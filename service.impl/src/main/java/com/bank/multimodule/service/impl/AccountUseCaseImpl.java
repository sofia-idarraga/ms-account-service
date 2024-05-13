package com.bank.multimodule.service.impl;

import com.bank.multimodule.model.Account;
import com.bank.multimodule.model.Client;
import com.bank.multimodule.model.Movement;
import com.bank.multimodule.model.commons.ErrorCode;
import com.bank.multimodule.model.commons.Result;
import com.bank.multimodule.repository.adapters.AccountAdapter;
import com.bank.multimodule.repository.adapters.MovementAdapter;
import com.bank.multimodule.rest.RestClientAdapter;
import com.bank.multimodule.service.AccountUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AccountUseCaseImpl implements AccountUseCase {

    private final RestClientAdapter clientAdapter;
    private final MovementAdapter movementAdapter;

    private final AccountAdapter accountAdapter;


    public AccountUseCaseImpl(RestClientAdapter clientAdapter, MovementAdapter movementAdapter, AccountAdapter accountAdapter) {
        this.clientAdapter = clientAdapter;
        this.movementAdapter = movementAdapter;
        this.accountAdapter = accountAdapter;
    }

    @Override
    public Result<Account> createAccount(Account account) {
        Result<Client> clientResult = clientAdapter.getClient(account.getClientNit().toString());
        return clientResult.isSuccess()
                ? accountAdapter.save(account)
                : Result.errorResult(ErrorCode.SERVER_ERROR,
                "Account could not be created due to client with given NIT could not be found ");
    }

    @Override
    public Result<Account> findById(Long nit) {
        return accountAdapter.findById(nit);
    }

    @Override
    public Result<List<Account>> findAll(String direction) {
        return accountAdapter.findAll(direction);
    }

    @Override
    public Result<List<Account>> findAll(int pageNumber, int pageSize, String sortDirection) {
        return accountAdapter.findAll(pageNumber, pageSize, sortDirection);
    }

    @Override
    public Result<Account> updateAccountInformation(Account account) {
        return accountAdapter.update(account);
    }

    @Override
    public Result<Boolean> deactivateAccounts(Long clientNit) {
        return accountAdapter.deactivateAccount(clientNit);
    }

    @Override
    public Result<Boolean> deleteById(Long nit) {
        Result<List<Movement>> movements = movementAdapter.findByAccountNumber(nit);

        Boolean result = movements.getValue().stream()
                .map(movement -> movementAdapter.deleteById(movement.getId()))
                .noneMatch(booleanResult -> Boolean.FALSE.equals(booleanResult.isSuccess()));

        if (Boolean.FALSE.equals(result)) {
            return Result.errorResult(ErrorCode.SERVER_ERROR, "Error deleting movements from account");
        }
        return accountAdapter.deleteById(nit);
    }
}
