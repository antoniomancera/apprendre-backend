package com.antonio.apprendrebackend.service.model;

import java.util.List;

public class Home {
    List<DailyStats> weekStats;
    Goal goal;


    public List<DailyStats> getWeekStats() {
        return weekStats;
    }

    public void setWeekStats(List<DailyStats> weekStats) {
        this.weekStats = weekStats;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Home() {
    }
}
