package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.exception.UserHistorialNotFoundException;
import com.antonio.apprendrebackend.service.mapper.UserHistorialMapper;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.UserHistorialRespository;
import com.antonio.apprendrebackend.service.service.UserHistorialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.antonio.apprendrebackend.service.util.GeneralConstants.ONE_DAY_MILLIS;

@Service
public class UserHistorialServiceImpl implements UserHistorialService {
    private static final Logger logger = LoggerFactory.getLogger(UserHistorialServiceImpl.class);

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
        logger.debug("Getting userHistorial of the last week");

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
        logger.debug("Getting last userHistorial");

        return userHistorialRespository.findFirstByUserInfoOrderByDateDesc(userInfo);
    }

    @Override
    public List<UserHistorialDTO> getDeckWordTranslationHistorialByDayMillis(UserInfo userInfo, Long dayMillis) {
        logger.debug("Getting the userHistorial of the day: %d", dayMillis);

        if (dayMillis == null) {
            throw new IllegalArgumentException("The argument is null");
        }

        List<UserHistorial> userHistorialList = userHistorialRespository.findByUserInfoAndDateBetweenOrderByDateDesc(userInfo, dayMillis, dayMillis + ONE_DAY_MILLIS - 1);
        if (userHistorialList.isEmpty() || userHistorialList.size() == 0) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate day = Instant.ofEpochMilli(dayMillis).atZone(ZoneId.systemDefault()).toLocalDate();
            throw new UserHistorialNotFoundException(
                    String.format("Not found any historial for the day %s", dateFormatter.format(day))
            );
        }

        Map<String, UserHistorialDTO> groupedHistorialMap = new HashMap<>();
        for (UserHistorial historial : userHistorialList) {
            Integer deckId = historial.getDeck().getId();
            Integer wordTranslationId = historial.getDeckWordPhraseTranslation().getId();

            String key = deckId + "-" + wordTranslationId;
            UserHistorialDTO existingHistorialDTO = groupedHistorialMap.get(key);

            if (existingHistorialDTO == null) {
                UserHistorialDTO newHistorialDTO = userHistorialMapper.toDTO(historial);
                newHistorialDTO.setAttempts(1);
                groupedHistorialMap.put(key, newHistorialDTO);
            } else {
                existingHistorialDTO.getSuccess().setScore(
                        existingHistorialDTO.getSuccess().getScore() + historial.getSuccess().getScore()
                );
                existingHistorialDTO.setAttempts(existingHistorialDTO.getAttempts() + 1);
            }
        }
        return new ArrayList<>(groupedHistorialMap.values());
    }

    /**
     * Save new UserHistorial
     *
     * @param userHistorial
     * @returnUserHistorial
     */
    @Override
    public UserHistorial postUserHistorial(UserHistorial userHistorial) {
        logger.debug("Saving a new UserHistorial");

        return userHistorialRespository.save(userHistorial);
    }
}
