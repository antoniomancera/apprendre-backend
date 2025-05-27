package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConjugationVariationRepository extends CrudRepository<ConjugationVariation, Integer> {
    Optional<ConjugationVariation> findByConjugationVerb(ConjugationVerb conjugationVerb);
}
