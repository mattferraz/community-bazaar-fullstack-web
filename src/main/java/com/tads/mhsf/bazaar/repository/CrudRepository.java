package com.tads.mhsf.bazaar.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T t);
    Optional<T> findById(ID id);
    void update(T t);
    void deleteById(ID id);
    List<T> findAll();
}
