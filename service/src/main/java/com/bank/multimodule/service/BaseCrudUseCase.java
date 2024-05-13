package com.bank.multimodule.service;

import com.bank.multimodule.model.commons.Result;

import java.util.List;

public interface BaseCrudUseCase<T, I> {
    Result<T> findById(I id);
    Result<List<T>> findAll(String direction);
    Result<List<T>> findAll(int pageNumber, int pageSize, String sortDirection);
    Result<Boolean> deleteById(I id);
}
