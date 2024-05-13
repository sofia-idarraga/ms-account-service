package com.bank.multimodule.service;

import com.bank.multimodule.model.Account;
import com.bank.multimodule.model.commons.Result;

public interface AccountUseCase extends BaseCrudUseCase<Account, Long>{

    Result<Account> createAccount(Account account);

    Result<Account> updateAccountInformation(Account account);

    Result<Boolean> deactivateAccounts(Long clientNit);

}