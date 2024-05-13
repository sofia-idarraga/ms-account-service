package com.bank.multimodule.repository.adapters;


import com.bank.multimodule.model.commons.Result;

import java.util.List;

public interface BaseAdapter<T, I> {

    Result<T> save(T model);

    Result<T> findById(I id);

    Result<T> update(T id);

    Result<List<T>> findAll(String direction);

    Result<List<T>> findAll(int pageNumber, int pageSize, String direction);

    Result<Boolean> deleteById(I id);

}
