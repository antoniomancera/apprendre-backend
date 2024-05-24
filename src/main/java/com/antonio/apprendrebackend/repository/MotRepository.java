package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.Mot;
import com.antonio.apprendrebackend.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface MotRepository extends CrudRepository<Mot, Integer> {
    Optional<Mot> findByName(String name);
}