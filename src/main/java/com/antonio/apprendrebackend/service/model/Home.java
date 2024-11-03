package com.antonio.apprendrebackend.service.model;

import com.antonio.apprendrebackend.service.dto.GoalDTO;

import java.util.List;

public class Home {
    List<DailyStats> weekStats;
    GoalDTO goal;


    public List<DailyStats> getWeekStats() {
        return weekStats;
    }

    public void setWeekStats(List<DailyStats> weekStats) {
        this.weekStats = weekStats;
    }

    public GoalDTO getGoal() {
        return goal;
    }

    public void setGoal(GoalDTO goal) {
        this.goal = goal;
    }

    public Home() {
    }
}
