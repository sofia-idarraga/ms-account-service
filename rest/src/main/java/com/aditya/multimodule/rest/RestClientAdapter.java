package com.aditya.multimodule.rest;

import com.aditya.multimodule.model.commons.Result;

public interface RestClientAdapter {

    Result<String> consume();
}