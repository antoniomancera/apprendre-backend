package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;

import java.util.Optional;

public interface ConjugationVariationService {
    /**
     * Return the ConjugationVariation related to a conjugationVerb, if is null the verb is regular
     *
     * @param conjugationVerb
     * @return Optional<ConjugationVariation>
     */
    Optional<ConjugationVariation> getByConjugationVerb(ConjugationVerb conjugationVerb);
}
