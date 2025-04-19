package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.UserGoal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.GoalRepository;
import com.antonio.apprendrebackend.service.service.UserGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGoalServiceImpl implements UserGoalService {
    @Autowired
    GoalRepository goalRepository;

    /**
     * Get the active goal for the logged User
     *
     * @param userInfo
     * @return UserGoal
     */
    @Override
    public UserGoal getActiveGoal(UserInfo userInfo) {
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
    public UserGoal createGoal(UserInfo userInfo, Integer attempts, Double successesAccuracy) {
        UserGoal lastUserGoal = goalRepository.findFirstByUserInfoOrderByBeginDateDesc(userInfo);
        if (lastUserGoal != null) {
            lastUserGoal.setEndDate(System.currentTimeMillis());
            goalRepository.save(lastUserGoal);
        }
        UserGoal goal = goalRepository.save(new UserGoal(userInfo, attempts, successesAccuracy));
        if (goal == null) {
            throw new GoalNotCreatedException("Goal not created");
        }
        return goal;
    }
}
