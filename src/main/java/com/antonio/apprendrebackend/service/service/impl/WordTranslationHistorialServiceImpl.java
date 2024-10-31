package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;
import com.antonio.apprendrebackend.service.repository.WordTranslationHistorialRepository;
import com.antonio.apprendrebackend.service.service.WordTranslationHistorialService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordTranslationHistorialServiceImpl implements WordTranslationHistorialService {
    @Autowired
    WordTranslationHistorialRepository wordTranslationHistorialRepository;

    @Override
    public List<WordTranslationHistorial> getWordTranslationHistorialLastWeek() {
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return wordTranslationHistorialRepository.findByDateGreaterThanEqualAndDateLessThan(startMillis, endMillis);
    }
}
