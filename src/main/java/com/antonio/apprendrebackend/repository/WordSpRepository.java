package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.WordSp;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface WordSpRepository extends CrudRepository<WordSp, Integer> {
    Optional<WordSp> findByName(String name);
}