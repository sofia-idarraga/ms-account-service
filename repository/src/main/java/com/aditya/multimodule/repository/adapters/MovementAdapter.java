package com.aditya.multimodule.repository.adapters;

import com.aditya.multimodule.model.Movement;
import com.aditya.multimodule.model.commons.Result;

import java.time.LocalDate;
import java.util.List;

public interface MovementAdapter extends BaseAdapter<Movement, Long> {

    Result<List<Movement>> findByAccountClientNitAndDateBetween(Long clientNit, LocalDate startDate, LocalDate endDate);
}
