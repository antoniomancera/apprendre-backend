package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.UserHistorial;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserHistorialRespository extends CrudRepository<UserHistorial, Integer> {
    /**
     * Get the DeckWord between two dates (start and end)
     *
     * @param start
     * @param end
     * @return
     */
    List<UserHistorial> findByUserInfoAndDateBetweenOrderByDateDesc(UserInfo userInfo, Long start, Long end);

    /**
     * Get the last DeckWordTranslationHistorial
     *
     * @return DeckWordTranslationHistorial
     */
    Optional<UserHistorial> findFirstByUserInfoOrderByDateDesc(UserInfo userInfo);
}

