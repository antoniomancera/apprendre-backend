package com.antonio.apprendrebackend.service.repository;


import com.antonio.apprendrebackend.service.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TypeRepository extends CrudRepository<Type, Integer> {
    Optional<Type> findByType(Type.TypeEnum typeEnum);
}