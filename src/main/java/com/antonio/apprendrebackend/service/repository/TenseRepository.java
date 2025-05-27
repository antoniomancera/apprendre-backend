package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Tense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TenseRepository extends CrudRepository<Tense, Integer> {
    List<Tense> findByLanguage(Language language);
}
