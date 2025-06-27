package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConjugationVerbFormIrregularRepository extends CrudRepository<ConjugationVerbFormIrregular, Integer> {
    List<ConjugationVerbFormIrregular> findByConjugationVariation(ConjugationVariation conjugationVariation);
}
