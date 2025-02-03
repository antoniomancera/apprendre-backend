package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationHistorialRespository;
import com.antonio.apprendrebackend.service.service.DeckWordTranslationHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class DeckWordTranslationHistorialServiceImpl implements DeckWordTranslationHistorialService {
    @Autowired
    DeckWordTranslationHistorialRespository deckWordTranslationHistorialRespository;

    @Override
    public Optional<List<DeckWordTranslationHistorial>> getWordTranslationHistorialLastWeek() {
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        System.out.println("Service startMillis: " + startMillis);
        System.out.println("Service endMillis: " + endMillis);
        return deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(startMillis, endMillis);
    }

    @Override
    public Optional<DeckWordTranslationHistorial> getLastWordTranslationHistorial() {
        return deckWordTranslationHistorialRespository.findFirstByOrderByDateDesc();
    }

}
