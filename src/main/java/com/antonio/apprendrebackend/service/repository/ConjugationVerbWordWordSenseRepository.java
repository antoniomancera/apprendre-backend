package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVerbWordWordSense;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConjugationVerbWordWordSenseRepository extends CrudRepository<ConjugationVerbWordWordSense, Integer> {
    Optional<ConjugationVerbWordWordSense> findByWordSenseId(Integer wordSenseId);

    Optional<ConjugationVerbWordWordSense> findByWordId(Integer wordSenseId);
}
