package com.aditya.multimodule.service;

import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.commons.Result;

import java.util.List;

public interface AccountUseCase extends BaseCrudUseCase<Account, Long>{

    Result<Account> createAccount(Account account);

    Result<Account> updateAccountInformation(Account Account);

    Result<Boolean> deactivateAccounts(Long clientNit);

}