package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Goal;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Integer> {
    Goal findFirstByOrderByDateDesc();
}
