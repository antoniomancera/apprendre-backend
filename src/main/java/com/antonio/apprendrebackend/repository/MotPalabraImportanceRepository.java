package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.MotPalabraImportance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MotPalabraImportanceRepository extends CrudRepository<MotPalabraImportance, Integer> {
    List<MotPalabraImportance> findAll();
}
