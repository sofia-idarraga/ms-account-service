package com.aditya.multimodule.repository.adapters;


import com.aditya.multimodule.model.Account;
import com.aditya.multimodule.model.commons.Result;

public interface AccountAdapter extends BaseAdapter<Account, Long> {

    Result<Boolean> deactivateAccount(Long clientId);
}
