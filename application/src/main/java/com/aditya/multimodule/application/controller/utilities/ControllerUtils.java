package com.aditya.multimodule.application.controller.utilities;

import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerUtils {
    public static <T> ResponseEntity<Result<T>> processResult(Result<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            ErrorCode errorCode = result.getErrors().get(0).getCode();
            return switch (errorCode) {
                case SERVER_ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
                case CLIENT_ERROR -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
                case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
                case GENERIC_ERROR -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
            };
        }
    }
}
