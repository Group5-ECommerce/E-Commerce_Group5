package com.christian.service;

import java.util.List;
import java.util.Optional;

// This post https://stackoverflow.com/a/61384615 was helpful in understanding how to abstract a service.
public interface AbstractService<T, Integer>{
    public abstract T save(T entity);
    public abstract List<T> findAll();

    public abstract Optional<T> findById(int entityId);
    public abstract T update(T entity);
    public abstract T updateById(T entity, int entityId);   
    public abstract void delete(T entity);
    public abstract void deleteById(int entityId);    
}