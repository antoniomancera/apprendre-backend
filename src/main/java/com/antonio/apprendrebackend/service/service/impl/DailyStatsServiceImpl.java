package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;
import com.antonio.apprendrebackend.service.service.DailyStatsService;
import com.antonio.apprendrebackend.service.service.WordTranslationHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyStatsServiceImpl implements DailyStatsService {
    @Autowired
    WordTranslationHistorialService wordTranslationHistorialService;

    @Override
    public List<DailyStats> getDailyStatsLastWeek() {
        List<WordTranslationHistorial> historialLastweek = wordTranslationHistorialService.getWordTranslationHistorialLastWeek();
        Map<LocalDate, DailyStats> dailyStatsMap = new HashMap<>();

        for (WordTranslationHistorial historialDay : historialLastweek) {
            LocalDate date = Instant.ofEpochMilli(historialDay.getDate())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            DailyStats stat = dailyStatsMap.computeIfAbsent(date, k -> new DailyStats(date));
            stat.setTotalAttempts(stat.getTotalAttempts() + 1);
            stat.setTotalSuccesses(stat.getTotalSuccesses() + historialDay.getSuccesses());
        }
        return new ArrayList<>(dailyStatsMap.values());
    }
}
