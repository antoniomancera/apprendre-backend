package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Mood;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoodRepository extends CrudRepository<Mood, Integer> {
    List<Mood> findAll();
}
