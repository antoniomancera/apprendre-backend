package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DailyStats;

import java.util.List;

public interface DailyStatsService {
    List<DailyStats> getDailyStatsLastWeek();
}
