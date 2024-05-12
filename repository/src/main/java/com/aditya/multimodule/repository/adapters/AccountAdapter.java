package com.aditya.multimodule.repository.adapters;


import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.commons.Result;

import java.util.List;

public interface AccountAdapter extends BaseAdapter<Account, Long> {

    Result<List<Account>> findAllAccountsByNit(Long clientNit);

    Result<Boolean> deactivateAccount(Long clientId);
}
