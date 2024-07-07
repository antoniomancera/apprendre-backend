package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.WordFr;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface WordFrRepository extends CrudRepository<WordFr, Integer> {
    Optional<WordFr> findByName(String name);
}