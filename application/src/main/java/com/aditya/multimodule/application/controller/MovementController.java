package com.aditya.multimodule.application.controller;

import com.aditya.multimodule.model.Movement;
import com.aditya.multimodule.model.commons.Result;
import com.aditya.multimodule.model.dto.ReportDTO;
import com.aditya.multimodule.model.dto.TransactionDTO;
import com.aditya.multimodule.service.MovementUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.aditya.multimodule.application.controller.utilities.ControllerUtils.processResult;


@RestController
@RequestMapping("/api/movimientos")
public class MovementController extends AbstractCrudController<Movement, Long, MovementUseCase> {


    public MovementController(MovementUseCase movementUseCase) {
        super(movementUseCase);
    }

    @PostMapping
    public ResponseEntity<Result<Movement>> saveMovement(@RequestBody TransactionDTO transactionDTO) {
        Result<Movement> result = useCase.createMovement(transactionDTO);
        return processResult(result);
    }

    @GetMapping("/cliente/{nit}")
    public ResponseEntity<Result<List<Movement>>> findMovementsByClientNit(@PathVariable Long nit) {
        Result<List<Movement>> result = useCase.findAllByClientNit(nit);
        return processResult(result);
    }


    @GetMapping("/reportes")
    public ResponseEntity<Result<List<ReportDTO>>> generateReport(@RequestParam String nit,
                                                                  @RequestParam String initDate,
                                                                  @RequestParam String endDate) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate initLocalDate = LocalDate.parse(initDate, format);
        LocalDate endLocalDate = LocalDate.parse(endDate, format);
        Result<List<ReportDTO>> result = useCase.generateReport(Long.parseLong(nit), initLocalDate, endLocalDate);
        return processResult(result);
    }


}
