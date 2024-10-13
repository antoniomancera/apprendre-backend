package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.WordTranslation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordTranslationRepository extends CrudRepository<WordTranslation, Integer> {
    List<WordTranslation> findAll();
}
