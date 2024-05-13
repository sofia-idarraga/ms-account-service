package com.bank.multimodule.model.commons;


public class ResultError {
    private final ErrorCode code;

    private final String message;
    private final Exception exception;

    public ResultError(ErrorCode code, String message, Exception exception) {
        this.code = code;
        this.exception = exception;
        this.message = message;
    }

    public ResultError(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
        this.exception = new Exception();
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
