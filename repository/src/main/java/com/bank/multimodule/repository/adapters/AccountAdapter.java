package com.bank.multimodule.repository.adapters;


import com.bank.multimodule.model.Account;
import com.bank.multimodule.model.commons.Result;

import java.util.List;

public interface AccountAdapter extends BaseAdapter<Account, Long> {

    Result<List<Account>> findAllAccountsByNit(Long clientNit);

    Result<Boolean> deactivateAccount(Long clientId);
}
