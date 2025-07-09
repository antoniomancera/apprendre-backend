package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.GoalRepository;
import com.antonio.apprendrebackend.service.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    GoalRepository goalRepository;

    /**
     * Get the active goal for the logged User
     *
     * @param userInfo
     * @return Goal
     */
    @Override
    public Goal getActiveGoal(UserInfo userInfo) {
        return goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo);
    }

    /**
     * Create a new goal for the logged User
     *
     * @param userInfo
     * @param attempts
     * @param successesAccuracy
     * @return HTTP respond with the Goal created
     * @throws GoalNotCreatedException if the goal is not created
     */
    @Override
    public Goal createGoal(UserInfo userInfo, Integer attempts, Double successesAccuracy) {
        Goal lastGoal = goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        if (lastGoal != null) {
            lastGoal.setEndDate(System.currentTimeMillis());
            goalRepository.save(lastGoal);
        }
        Goal goal = goalRepository.save(new Goal(userInfo, attempts, successesAccuracy));
        if (goal == null) {
            throw new GoalNotCreatedException("Goal not created");
        }
        return goal;
    }
}
