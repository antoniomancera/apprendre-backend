package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.mapper.GoalMapper;
import com.antonio.apprendrebackend.service.mapper.UserInfoMapper;
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
    GoalService goalService;
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DeckService deckService;
    @Autowired
    WordTranslationHistorialService wordTranslationHistorialService;

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
     * @return
     */
    @Override
    public Home getHome() {
        Home home = new Home();
        home.setWeekStats(statsService.getDailyStatsLastWeek());
        home.setGoal(goalMapper.toDTO(goalService.getActiveGoal()));
        home.setUserInfo(userInfoMapper.toDTO(userInfoService.getUserInfo()));
        home.setDecks(Optional.ofNullable(deckService.getActiveDecks())
                .orElse(Collections.emptyList()).stream()
                .map(deck -> deckMapper.toDTO(deck))
                .collect(Collectors.toList()));
        home.setLastDeckId(wordTranslationHistorialService.getLastWordTranslationHistorial().getDeckId());

        return home;
    }
}
