package com.aditya.multimodule.rest;

import com.aditya.multimodule.model.Client;
import com.aditya.multimodule.model.commons.Result;

public interface RestClientAdapter {

    Result<Client> getClient(String nit);
}
