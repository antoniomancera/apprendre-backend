package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.model.ErrorResponse;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.service.GoalService;
import com.antonio.apprendrebackend.service.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/goal")
public class GoalController {
    @Autowired
    GoalService goalService;

    @PostMapping
    public @ResponseBody ResponseEntity<?> createGoal(
            @RequestParam(required = false, defaultValue = "0") Integer attempts,
            @RequestParam(required = false, defaultValue = "0.0") Double successesAccuracy) {
        Goal goal = goalService.createGoal(attempts, successesAccuracy);
        if (goal == null) {
            ErrorResponse errorResponse = new ErrorResponse("no se ha creado el GOAL", ErrorCode.GOAL_NOT_CREATED);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }
}


