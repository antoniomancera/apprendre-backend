package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.repository.GoalRepository;
import com.antonio.apprendrebackend.service.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    GoalRepository goalRepository;

    @Override
    public Goal getActiveGoal() {
        return goalRepository.findFirstByOrderByBeginDateDesc();
    }

    @Override
    public Goal createGoal(Integer attempts, Double successesAccuracy) {
        Goal lastGoal = goalRepository.findFirstByOrderByBeginDateDesc();
        if (lastGoal != null) {
            lastGoal.setEndDate(System.currentTimeMillis());
            goalRepository.save(lastGoal);
        }
        return goalRepository.save(new Goal(attempts, successesAccuracy));
    }
}
