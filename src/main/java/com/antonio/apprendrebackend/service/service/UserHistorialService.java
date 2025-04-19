package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.UserHistorialDTO;
import com.antonio.apprendrebackend.service.exception.DeckWordTranslationHistorialNotFoundException;
import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserHistorialService {
    /**
     * Get the userHistorial of the last week
     *
     * @return List<UserHistorial>
     */
    List<UserHistorial> getUserHistorialLastWeek(UserInfo userInfo);

    /**
     * Get the last element of an user in their historial
     *
     * @return UserHistorial
     */
    Optional<UserHistorial> getLastUserHistorial(UserInfo userInfo);

    /**
     * Return all DeckWordTranslationHistorial for a day
     *
     * @param dayMillis
     * @return List<DeckWordTranslationHistorialDTO>
     * @throws DeckWordTranslationHistorialNotFoundException if not exist any DeckWordTranslationHistorial the dayMillis day
     */
    List<UserHistorialDTO> getDeckWordTranslationHistorialByDayMillis(UserInfo userInfo, Long dayMillis);
}
