package com.bank.multimodule.repository.adapters;

import com.bank.multimodule.model.Movement;
import com.bank.multimodule.model.commons.Result;

import java.time.LocalDate;
import java.util.List;

public interface MovementAdapter extends BaseAdapter<Movement, Long> {

    Result<List<Movement>> findByAccountClientNitAndDateBetween(Long clientNit, LocalDate startDate, LocalDate endDate);

    Result<List<Movement>> findByAccountNumber(Long accountNumber);
}
