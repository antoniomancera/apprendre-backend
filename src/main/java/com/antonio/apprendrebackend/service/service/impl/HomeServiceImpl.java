package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.mapper.GoalMapper;
import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.service.DailyStatsService;
import com.antonio.apprendrebackend.service.service.GoalService;
import com.antonio.apprendrebackend.service.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    DailyStatsService dailyStatsService;

    @Autowired
    GoalService goalService;

    @Autowired
    GoalMapper goalMapper;

    @Override
    public Home getHome() {
        Home home = new Home();
        home.setWeekStats(dailyStatsService.getDailyStatsLastWeek());
        home.setGoal(goalMapper.toDTO(goalService.getActiveGoal()));
        return home;
    }
}
