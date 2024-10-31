package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Goal;

public interface GoalService {
    Goal getActiveGoal();

    Goal createGoal(Integer attempts, Double successesAccuracy);
}
