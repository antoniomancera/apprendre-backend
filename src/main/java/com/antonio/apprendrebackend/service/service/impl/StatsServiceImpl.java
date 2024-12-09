package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.WordTranslationHistorialNotFound;
import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;
import com.antonio.apprendrebackend.service.service.StatsService;
import com.antonio.apprendrebackend.service.service.WordTranslationHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    WordTranslationHistorialService wordTranslationHistorialService;

    /**
     * Returns a list with the historial of the last week
     *
     * @return List<DailyStats>
     */
    @Override
    public List<DailyStats> getDailyStatsLastWeek() {

        Optional<List<WordTranslationHistorial>> optionalHistorialLastWeek = wordTranslationHistorialService.getWordTranslationHistorialLastWeek();

        if (optionalHistorialLastWeek.isEmpty()) {
            throw new WordTranslationHistorialNotFound("Not found any Word Translation Historial in last week");
        }

        Map<LocalDate, DailyStats> dailyStatsMap = new HashMap<>();
        optionalHistorialLastWeek.ifPresent(historialLastWeek -> {
            for (WordTranslationHistorial historialDay : historialLastWeek) {
                LocalDate date = Instant.ofEpochMilli(historialDay.getDate())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                DailyStats stat = dailyStatsMap.computeIfAbsent(date, k -> new DailyStats(date));
                stat.setTotalAttempts(stat.getTotalAttempts() + 1);
                stat.setTotalSuccesses(stat.getTotalSuccesses() + historialDay.getSuccesses());
            }
        });

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);
        for (LocalDate date = startDate; !date.isAfter(today); date = date.plusDays(1)) {
            LocalDate finalDate = date;
            dailyStatsMap.computeIfAbsent(date, k -> new DailyStats(finalDate));
        }

        return dailyStatsMap.values().stream()
                .sorted(Comparator.comparing(DailyStats::getDate))
                .toList();

    }
}
