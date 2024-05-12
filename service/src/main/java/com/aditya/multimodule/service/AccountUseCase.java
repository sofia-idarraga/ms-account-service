package com.aditya.multimodule.service;

import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.commons.Result;

import java.util.List;

public interface AccountUseCase {

    Result<Account> createAccount(Account account);

    Result<Account> findAccountByNumber(Long nit);

    Result<List<Account>> findAllAccounts(String direction);

    Result<List<Account>> findAllAccounts(int pageNumber, int pageSize, String sortDirection);

    Result<Account> updateAccountInformation(Account Account);

    Result<Boolean> deactivateAccounts(Long clientNit);

    Result<Boolean> deleteAccountByNumber(Long nit);

}