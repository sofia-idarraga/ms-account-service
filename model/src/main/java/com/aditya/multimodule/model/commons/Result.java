package com.aditya.multimodule.model.commons;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Result<T> {

    private T value;
    private List<ResultError> errors;

    public Result() {
        this.errors = new ArrayList<>();
    }

    public Result(T value) {
        this.value = value;
        this.errors = new ArrayList<>();
    }


    public boolean isSuccess() {
        return getErrors().isEmpty();
    }

    public Result<T> addError(ResultError error) {
        getErrors().add(error);
        return this;
    }

    public Result<T> addError(String message) {
        ResultError error = new ResultError(ErrorCode.GENERIC_ERROR, message);
        addError(error);
        return this;
    }

    public List<ResultError> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }

        return this.errors;
    }

    public Boolean validateErrorCode(ErrorCode errorCode) {
        return this.errors.stream().anyMatch(metroError -> metroError.getCode().equals(errorCode));
    }
}
