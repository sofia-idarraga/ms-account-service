package com.aditya.multimodule.service.impl;

import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.Client;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.repository.adapters.AccountAdapter;
import com.aditya.multimodule.rest.RestClientAdapter;
import com.aditya.multimodule.service.AccountUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AccountUseCaseImpl implements AccountUseCase {

    private final RestClientAdapter clientAdapter;

    private final AccountAdapter accountAdapter;


    public AccountUseCaseImpl(RestClientAdapter clientAdapter, AccountAdapter accountAdapter) {
        this.clientAdapter = clientAdapter;
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
        return accountAdapter.deleteById(nit);
    }
}
