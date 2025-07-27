package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordSense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordSenseRepository extends CrudRepository<WordSense, Integer> {
    List<WordSense> findByWordId(Integer wordId);
}
