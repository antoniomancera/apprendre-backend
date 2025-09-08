package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.StatsService;
import com.antonio.apprendrebackend.service.service.UserHistorialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {
    private static final Logger logger = LoggerFactory.getLogger(StatsServiceImpl.class);

    @Autowired
    UserHistorialService userHistorialService;

    /**
     * Returns a list with the historial of the last week
     *
     * @return List<DailyStats>
     */
    @Override
    public List<DailyStats> getDailyStatsLastWeek(UserInfo userInfo) {
        logger.debug("Getting the stats for logged user");

        List<UserHistorial> historialLastWeek = userHistorialService.getUserHistorialLastWeek(userInfo);

        Map<LocalDate, DailyStats> dailyStatsMap = new HashMap<>();
        for (UserHistorial historialDay : historialLastWeek) {
            LocalDate date = Instant.ofEpochMilli(historialDay.getDate())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            DailyStats stat = dailyStatsMap.computeIfAbsent(date, k -> new DailyStats(date));
            stat.setTotalAttempts(stat.getTotalAttempts() + 1);
            stat.setTotalSuccesses((int) (stat.getTotalSuccesses() + historialDay.getSuccess().getScore()));
        }

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
