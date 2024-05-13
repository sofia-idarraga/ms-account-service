package com.bank.multimodule.rest;

import com.bank.multimodule.model.Client;
import com.bank.multimodule.model.commons.Result;

public interface RestClientAdapter {

    Result<Client> getClient(String nit);
}
