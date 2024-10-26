package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.repository.GoalRepository;
import com.antonio.apprendrebackend.service.service.HomeService;
import com.antonio.apprendrebackend.service.service.WordTranslationHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    WordTranslationHistorialService wordTranslationHistorialService;

    @Autowired
    GoalRepository goalRepository;

    @Override
    public Home getHome() {
        Home home = new Home();
        home.setWeekStats(wordTranslationHistorialService.getWordTranslationHistorialLastWeek());
        home.setGoal(goalRepository.findFirstByOrderByDateDesc());
        return home;
    }
}
