package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Level;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LevelRepository extends CrudRepository<Level, Integer> {
    List<Level> findAll();
}
