package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.PhraseTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface PhraseTranslationRepository extends CrudRepository<PhraseTranslation, Integer> {
    /**
     * Get a page with phrases
     *
     * @param pageable
     * @return
     */
    Page<PhraseTranslation> findAll(Pageable pageable);
}
