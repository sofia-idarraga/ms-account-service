package com.aditya.multimodule.application.controller;

import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.service.BaseCrudUseCase;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.aditya.multimodule.application.controller.utilities.ControllerUtils.processResult;

public abstract class AbstractCrudController<M, I, S extends BaseCrudUseCase<M, I>> {

    protected final S useCase;

    protected AbstractCrudController(S useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<M>> findById(@PathVariable I id) {
        Result<M> result = useCase.findById(id);
        return processResult(result);
    }

    @GetMapping("/todos")
    public ResponseEntity<Result<List<M>>> findAll(@RequestParam(defaultValue = "ASC") String direction) {
        String sort = "ASC";
        if (direction.equals("ASC") || direction.equals("DESC")) {
            sort = direction;
        }
        Result<List<M>> result = useCase.findAll(sort);
        return processResult(result);
    }

    @GetMapping("/todos/page")
    public ResponseEntity<Result<List<M>>> findAllPage(@RequestParam(defaultValue = "1") int pageNumber,
                                                               @RequestParam(defaultValue = "10") int pageSize,
                                                               @RequestParam(defaultValue = "ASC") String direction) {
        String sort = "ASC";
        if (direction.equals("ASC") || direction.equals("DESC")) {
            sort = direction;
        }
        Result<List<M>> result = useCase.findAll(pageNumber, pageSize, sort);
        return processResult(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Boolean>> deleteById(@PathVariable I id) {
        Result<Boolean> result = useCase.deleteById(id);
        return processResult(result);
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseEntity<Result<T>> handleDateTimeParseException(DateTimeParseException ex) {
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseEntity<Result<T>> handleLongPase(NumberFormatException ex) {
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseEntity<Result<T>> handleConversionFailedException(ConversionFailedException ex) {
        return badRequestResponse(ex.getMessage());
    }

    private static <T> ResponseEntity<Result<T>> badRequestResponse(String ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.errorResult(ErrorCode.CLIENT_ERROR, ex));
    }
}
