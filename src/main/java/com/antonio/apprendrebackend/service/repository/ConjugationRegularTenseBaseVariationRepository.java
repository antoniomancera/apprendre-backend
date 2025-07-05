package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationRegularTenseBaseVariation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConjugationRegularTenseBaseVariationRepository extends CrudRepository<ConjugationRegularTenseBaseVariation, Integer> {
    Optional<ConjugationRegularTenseBaseVariation> findByTenseId(Integer tenseId);
}
