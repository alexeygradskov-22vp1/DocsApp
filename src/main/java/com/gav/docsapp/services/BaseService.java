package com.gav.docsapp.services;

import com.gav.docsapp.entities.BaseEntity;
import com.gav.docsapp.lists.BaseList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, V extends JpaRepository<T,Long>>{
    protected V _repository;
    public BaseService(V repository){
        _repository = repository;
    }

    public abstract void add(T elem);
    public abstract Optional<T> getOne(long id);

    public abstract BaseList<T> getAll();

    public abstract void update(T updatable);

    public abstract void delete(long id);
}
