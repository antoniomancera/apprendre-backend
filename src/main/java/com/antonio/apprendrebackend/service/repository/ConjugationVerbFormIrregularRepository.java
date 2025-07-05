package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConjugationVerbFormIrregularRepository extends CrudRepository<ConjugationVerbFormIrregular, Integer> {
    List<ConjugationVerbFormIrregular> findByConjugationVariation(ConjugationVariation conjugationVariation);

    Optional<ConjugationVerbFormIrregular> findByConjugationVariationAndConjugationVerbForm(ConjugationVariation conjugationVariation, ConjugationVerbForm conjugationVerbForm);
}