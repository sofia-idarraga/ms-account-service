package com.aditya.multimodule.application.controller;

import com.aditya.multimodule.model.Movement;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.model.dto.ReportDTO;
import com.aditya.multimodule.model.dto.TransactionDTO;
import com.aditya.multimodule.service.MovementUseCase;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@RestController
@RequestMapping("/api/movimientos")
public class MovementController {

    private final MovementUseCase movementUseCase;

    public MovementController(MovementUseCase movementUseCase) {
        this.movementUseCase = movementUseCase;
    }

    @PostMapping
    public ResponseEntity<Result<Movement>> saveMovement(@RequestBody TransactionDTO transactionDTO) {
        Result<Movement> result = movementUseCase.createMovement(transactionDTO);
        return processResult(result);
    }

    @GetMapping("/cliente/{nit}")
    public ResponseEntity<Result<List<Movement>>> findMovementsByClientNit(@PathVariable Long nit) {
        Result<List<Movement>> result = movementUseCase.findAllByClientNit(nit);
        return processResult(result);
    }


    @GetMapping("/reportes")
    public ResponseEntity<Result<List<ReportDTO>>> generateReport(@RequestParam String nit,
                                                                  @RequestParam String initDate,
                                                                  @RequestParam String endDate) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate initLocalDate = LocalDate.parse(initDate, format);
        LocalDate endLocalDate = LocalDate.parse(endDate, format);
        Result<List<ReportDTO>> result = movementUseCase.generateReport(Long.parseLong(nit), initLocalDate, endLocalDate);
        return processResult(result);
    }


    private <T> ResponseEntity<Result<T>> processResult(Result<T> result) {
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

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result<List<ReportDTO>>> handleDateTimeParseException(DateTimeParseException ex) {
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result<List<ReportDTO>>> handleLongPase(NumberFormatException ex) {
        return badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result<List<ReportDTO>>> handleConversionFailedException(ConversionFailedException ex) {
        return badRequestResponse(ex.getMessage());
    }

    private static ResponseEntity<Result<List<ReportDTO>>> badRequestResponse(String ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.errorResult(ErrorCode.CLIENT_ERROR, ex));
    }
}
