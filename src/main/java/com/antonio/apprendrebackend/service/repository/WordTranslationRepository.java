package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordTranslationRepository extends CrudRepository<WordTranslation, Integer> {
    /**
     * Get a page with WordTranslation
     *
     * @param pageable
     * @return
     */
    Page<WordTranslation> findAll(Pageable pageable);
}
