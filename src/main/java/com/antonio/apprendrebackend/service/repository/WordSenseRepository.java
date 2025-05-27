package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordSense;
import org.springframework.data.repository.CrudRepository;

public interface WordSenseRepository extends CrudRepository<WordSense, Integer> {
}
