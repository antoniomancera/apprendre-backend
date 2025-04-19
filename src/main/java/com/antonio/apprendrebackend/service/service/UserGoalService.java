package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.UserGoal;
import com.antonio.apprendrebackend.service.model.UserInfo;

public interface UserGoalService {
    /**
     * Get the active goal for the logged User
     *
     * @param userInfo
     * @return UserGoal
     */
    UserGoal getActiveGoal(UserInfo userInfo);

    /**
     * Create a new goal for the logged User
     *
     * @param userInfo
     * @param attempts
     * @param successesAccuracy
     * @return HTTP respond with the Goal created
     * @throws GoalNotCreatedException if the goal is not created
     */
    UserGoal createGoal(UserInfo userInfo, Integer attempts, Double successesAccuracy);

}

