package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
import com.antonio.apprendrebackend.service.mapper.UserHistorialMapper;
import com.antonio.apprendrebackend.service.model.DeckUserWordPhraseTranslation;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.WordTranslation;
import com.antonio.apprendrebackend.service.repository.DeckUserWordPhraseTranslationRespository;
import com.antonio.apprendrebackend.service.repository.UserHistorialRespository;
import com.antonio.apprendrebackend.service.service.UserHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;

@Service
public class UserHistorialServiceImpl implements UserHistorialService {
    @Autowired
    UserHistorialRespository userHistorialRespository;

    @Autowired
    UserHistorialMapper userHistorialMapper;

    /**
     * Get the userHistorial of the last week
     *
     * @return List<UserHistorial>
     */
    @Override
    public List<UserHistorial> getUserHistorialLastWeek(UserInfo userInfo) {
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(userInfo, startMillis, endMillis);
    }

    /**
     * Get the last element of an user in their historial
     *
     * @return UserHistorial
     */
    @Override
    public Optional<UserHistorial> getLastUserHistorial(UserInfo userInfo) {
        return userHistorialRespository.findFirstByUserInfoOrderByDateDesc(userInfo);
    }

    @Override
    public List<UserHistorialDTO> getDeckWordTranslationHistorialByDayMillis(UserInfo userInfo, Long dayMillis) {
        if (dayMillis == null) {
            throw new IllegalArgumentException("The argument is null");
        }

        List<UserHistorial> userHistorialList = userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(userInfo, dayMillis, dayMillis + ONE_DAY_MILLIS - 1);
        if (userHistorialList.isEmpty() || userHistorialList.size() == 0) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate day = Instant.ofEpochMilli(dayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
            throw new DeckWordTranslationHistorialNotFoundException(
                    String.format("Not found any historial for the day %s", dateFormatter.format(day))
            );
        }

        Map<String, UserHistorialDTO> groupedHistorialMap = new HashMap<>();
        for (UserHistorial historial : userHistorialList) {
            Integer deckId = historial.getDeckId();
            Integer wordTranslationId = historial.getWordTranslation().getId();

            String key = deckId + "-" + wordTranslationId;
            UserHistorialDTO existingHistorialDTO = groupedHistorialMap.get(key);

            if (existingHistorialDTO == null) {
                UserHistorialDTO newHistorialDTO = userHistorialMapper.toDTO(historial);
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
