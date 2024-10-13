package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordTranslationPool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WordTranslationPoolRepository extends CrudRepository<WordTranslationPool, Integer> {
    @Query(value = "SELECT * FROM word_translation_pool ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    WordTranslationPool findRandomWordTranslationPool();
}
