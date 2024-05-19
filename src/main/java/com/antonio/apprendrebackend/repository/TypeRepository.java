package com.antonio.apprendrebackend.repository;


import com.antonio.apprendrebackend.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TypeRepository extends CrudRepository<Type, Integer> {
    Optional<Type> findByTypeEnum(Type.TypeEnum typeEnum);
}