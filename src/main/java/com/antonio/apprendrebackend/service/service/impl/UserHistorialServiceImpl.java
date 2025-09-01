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
        logger.debug("Called getUserHistorialLastWeek() in UserHistorailService for userId-{}", userInfo.getId());

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
        logger.debug("Called getLastUserHistorial() in UserHistorailService for userId-{}", userInfo.getId());

        return userHistorialRespository.findFirstByUserInfoOrderByDateDesc(userInfo);
    }

    @Override
    public List<UserHistorialDTO> getDeckWordTranslationHistorialByDayMillis(UserInfo userInfo, Long dayMillis) {
        logger.debug("Called getDeckWordTranslationHistorialByDayMillis() in UserHistorailService for userId-{}, of the day-{}", userInfo.getId(), dayMillis);

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
        logger.debug("Called postUserHistorial() in UserHistorailService for userHistorial-{}", userHistorial);

        return userHistorialRespository.save(userHistorial);
    }

    /**
     * Get the UserHistorials of a word for a user
     *
     * @param userId
     * @param wordId
     * @return List<UserHistorial>
     */
    @Override
    public List<UserHistorial> getUserHistorialsByUserIdAndWordId(Integer userId, Integer wordId) {
        logger.debug("Called postUserHistorial() in UserHistorailService for userHistorial-{} and word-{}", userId, wordId);

        return userHistorialRespository.findByUserInfoIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(userId, wordId);
    }

    /**
     * Get all the UserHistorials of a word in a deck
     *
     * @param deckId
     * @param wordId
     * @return List<UserHistorial>
     */
    @Override
    public List<UserHistorial> getUserHistorialsByDeckIdAndWordId(Integer deckId, Integer wordId) {
        logger.debug("Called postUserHistorial() in UserHistorailService for deck-{} and word-{}", deckId, wordId);

        return userHistorialRespository.findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrWordId(deckId, wordId);
    }

    /**
     * Get all the UserHistorials of a wordSense in a deck
     *
     * @param deckId
     * @param wordSenseId
     * @return List<UserHistorial>
     */
    @Override
    public List<UserHistorial> getUserHistorialsByDeckIdAndWordSenseId(Integer deckId, Integer wordSenseId) {
        logger.debug("Called postUserHistorial() in UserHistorailService for deck-{} and wordSense-{}", deckId, wordSenseId);

        return userHistorialRespository.findByDeckWordPhraseTranslationDeckIdAndDeckWordPhraseTranslationWordPhraseTranslationWordTranslationWordSenseFrId(deckId, wordSenseId);
    }
}
