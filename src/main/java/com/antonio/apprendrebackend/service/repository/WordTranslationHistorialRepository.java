package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordTranslationHistorial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordTranslationHistorialRepository extends CrudRepository<WordTranslationHistorial, Integer> {
    List<WordTranslationHistorial> findByDateGreaterThanEqualAndDateLessThanOrderByDateDesc(Long start, Long end);

    WordTranslationHistorial findFirstByOrderByDateDesc();
}
