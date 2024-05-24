package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.Mot;
import com.antonio.apprendrebackend.model.Palabra;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface PalabraRepository extends CrudRepository<Palabra, Integer> {
    Optional<Palabra> findByName(String name);
}