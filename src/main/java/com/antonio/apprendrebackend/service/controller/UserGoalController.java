package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
import com.antonio.apprendrebackend.service.exception.GoalNotCreatedException;
import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.model.UserGoal;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.UserGoalService;
import com.antonio.apprendrebackend.service.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/goal")
public class UserGoalController {
    @Autowired
    UserGoalService userGoalService;


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
        UserInfo userInfo = new UserInfo(1, "1");
        UserGoal createdUserGoal = userGoalService.createGoal(userInfo, goal.getAttempts(), goal.getSuccessesAccuracy());
        return ResponseEntity.ok(createdUserGoal);
    }
}


