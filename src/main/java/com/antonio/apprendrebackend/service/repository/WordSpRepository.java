package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordSp;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface WordSpRepository extends CrudRepository<WordSp, Integer> {
    Optional<WordSp> findByName(String name);
}