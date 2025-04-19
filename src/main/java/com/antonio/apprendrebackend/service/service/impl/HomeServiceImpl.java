package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.HomeNotFoundException;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.mapper.GoalMapper;
import com.antonio.apprendrebackend.service.mapper.UserInfoMapper;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    UserGoalService userGoalService;
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DeckUserService deckUserService;
    @Autowired
    UserHistorialService userHistorialService;

    @Autowired
    StatsService statsService;

    @Autowired
    GoalMapper goalMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    DeckMapper deckMapper;

    /**
     * Returns the information to be displayed in home
     *
     * @return Home respond with a Home object that includes userHistorial among other data
     * @throws HomeNotFoundException if not found any data related to home for an user
     */
    @Override
    public Home getHome(UserInfo userInfo) {
        Home home = new Home();
        home.setWeekStats(statsService.getDailyStatsLastWeek(userInfo));
        home.setGoal(goalMapper.toDTO(userGoalService.getActiveGoal(userInfo)));
        home.setDecks(Optional.ofNullable(deckUserService.getActiveDecks(userInfo))
                .orElse(Collections.emptyList()).stream()
                .map(deck -> deckMapper.toDTO(deck))
                .collect(Collectors.toList()));

        home.setLastDeckId(userHistorialService.getLastUserHistorial(userInfo)
                .map(UserHistorial::getDeckId)
                .orElse(null));

        if (home == null) {
            throw new HomeNotFoundException("Home not found");
        }
        return home;
    }
}
