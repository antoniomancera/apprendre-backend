package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/stats")
public class StatsController {
    @Autowired
    StatsService statsService;

    /**
     * Returns a list with the historial of the last week
     *
     * @return List<DailyStats>
     */
    @GetMapping
    public @ResponseBody ResponseEntity<?> getStatsPageInitial() {
        List<DailyStats> stats = statsService.getDailyStatsLastWeek();
        return ResponseEntity.ok(stats);
    }


}
