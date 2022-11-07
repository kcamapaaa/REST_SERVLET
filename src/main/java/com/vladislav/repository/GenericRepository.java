package com.vladislav.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);
    List<T> getAll();
    T save(T t);
    T update(T t);
    boolean deleteById(ID id);

}
