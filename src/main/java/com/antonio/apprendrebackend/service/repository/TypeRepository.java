package com.antonio.apprendrebackend.service.repository;


import com.antonio.apprendrebackend.service.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface TypeRepository extends CrudRepository<Type, Integer> {
    List<Type> findAll();

    Optional<Type> findByCode(Type.TypeEnum code);
}