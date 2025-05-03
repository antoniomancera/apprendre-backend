package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface GoalService {
    /**
     * Get the active goal for the logged User
     *
     * @param userInfo
     * @return Goal
     */
    Goal getActiveGoal(UserInfo userInfo);

    /**
     * Create a new goal for the logged User
     *
     * @param userInfo
     * @param attempts
     * @param successesAccuracy
     * @return HTTP respond with the Goal created
     * @throws GoalNotCreatedException if the goal is not created
     */
    Goal createGoal(UserInfo userInfo, Integer attempts, Double successesAccuracy);

}

