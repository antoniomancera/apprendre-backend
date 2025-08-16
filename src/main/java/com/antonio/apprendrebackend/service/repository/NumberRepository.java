package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Number;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NumberRepository extends CrudRepository<Number, Integer> {
    List<Number> findAll();
}
