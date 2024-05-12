package com.aditya.multimodule.repository.adapters;



import com.aditya.multimodule.model.commons.Result;

import java.util.List;

public interface BaseAdapter<T, ID> {

    Result<T> save(T model);
    Result<T> findById(ID id);
    Result<T> update(T id);
    Result<List<T>> findAll(String direction);
    Result<List<T>> findAll(int pageNumber, int pageSize, String direction);
    Result<Boolean> deleteById(ID id);

}
