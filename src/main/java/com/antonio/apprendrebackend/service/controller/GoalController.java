package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/goal")
public class GoalController {
    @Autowired
    GoalService goalService;


    /**
     * Create a new goal for the logged User
     *
     * @param goal
     * @return HTTP respond with the Goal created
     * @throws GoalNotCreatedException if the goal is not created
     */
    @PostMapping
    public @ResponseBody ResponseEntity<?> createGoal(
            @RequestBody GoalDTO goal) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Goal createdGoal = goalService.createGoal(userInfo, goal.getAttempts(), goal.getSuccessesAccuracy());
        return ResponseEntity.ok(createdGoal);
    }
}


