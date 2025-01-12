package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.DeckWordTranslationHistorial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeckWordTranslationHistorialRespository extends CrudRepository<DeckWordTranslationHistorial, Integer> {
    /**
     * Get the DeckWord between two dates (start and end)
     *
     * @param start
     * @param end
     * @return
     */
    Optional<List<DeckWordTranslationHistorial>> findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(Long start, Long end);

    /**
     * Get the last DeckWordTranslationHistorial
     *
     * @return DeckWordTranslationHistorial
     */
    Optional<DeckWordTranslationHistorial> findFirstByOrderByDateDesc();
}

