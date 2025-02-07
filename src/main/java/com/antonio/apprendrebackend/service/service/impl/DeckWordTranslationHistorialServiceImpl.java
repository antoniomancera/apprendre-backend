package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.DeckWordTranslationHistorialDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
import com.antonio.apprendrebackend.service.mapper.DeckWordTranslationHistorialMapper;
import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import com.antonio.apprendrebackend.service.repository.DeckWordTranslationHistorialRespository;
import com.antonio.apprendrebackend.service.service.DeckWordTranslationHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;

@Service
public class DeckWordTranslationHistorialServiceImpl implements DeckWordTranslationHistorialService {
    @Autowired
    DeckWordTranslationHistorialRespository deckWordTranslationHistorialRespository;

    @Autowired
    DeckWordTranslationHistorialMapper deckWordTranslationHistorialMapper;

    @Override
    public Optional<List<DeckWordTranslationHistorial>> getWordTranslationHistorialLastWeek() {
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(startMillis, endMillis);
    }

    @Override
    public Optional<DeckWordTranslationHistorial> getLastWordTranslationHistorial() {
        return deckWordTranslationHistorialRespository.findFirstByOrderByDateDesc();
    }

    @Override
    public List<DeckWordTranslationHistorialDTO> getDeckWordTranslationHistorialByDayMillis(Long dayMillis) {
        if (dayMillis == null) {
            throw new IllegalArgumentException("The argument is null");
        }

        Optional<List<DeckWordTranslationHistorial>> optionalDeckWordTranslationHistorials = deckWordTranslationHistorialRespository.findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(dayMillis, dayMillis + ONE_DAY_MILLIS - 1);
        if (optionalDeckWordTranslationHistorials.isEmpty() || optionalDeckWordTranslationHistorials.get().size() == 0) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate day = Instant.ofEpochMilli(dayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
            throw new DeckWordTranslationHistorialNotFoundException(
                    String.format("Not found any historial for the day %s", dateFormatter.format(day))
            );
        }
        List<DeckWordTranslationHistorial> deckWordTranslationHistorialList = optionalDeckWordTranslationHistorials.get();

        Map<String, DeckWordTranslationHistorialDTO> groupedHistorialMap = new HashMap<>();
        for (DeckWordTranslationHistorial historial : deckWordTranslationHistorialList) {
            Integer deckId = historial.getDeckId();
            Integer wordTranslationId = historial.getWordTranslation().getId();

            String key = deckId + "-" + wordTranslationId;
            DeckWordTranslationHistorialDTO existingHistorialDTO = groupedHistorialMap.get(key);

            if (existingHistorialDTO == null) {
                DeckWordTranslationHistorialDTO newHistorialDTO = deckWordTranslationHistorialMapper.toDTO(historial);
                newHistorialDTO.setAttempts(1);
                groupedHistorialMap.put(key, newHistorialDTO);
            } else {
                existingHistorialDTO.setSuccess(existingHistorialDTO.getSuccess() + historial.getSuccess());
                existingHistorialDTO.setAttempts(existingHistorialDTO.getAttempts() + 1);
            }
        }
        return new ArrayList<>(groupedHistorialMap.values());
    }

}
