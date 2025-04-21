package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.UserInfo;

import java.util.List;

public interface StatsService {
    /**
     * Returns a list with the historial of the last week
     *
     * @return List<DailyStats>
     */
    List<DailyStats> getDailyStatsLastWeek(UserInfo userInfo);
}
