package com.antonio.apprendrebackend.repository;

import com.antonio.apprendrebackend.model.WordTranslation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordTranslationRepository extends CrudRepository<WordTranslation, Integer> {
    List<WordTranslation> findAll();
}
