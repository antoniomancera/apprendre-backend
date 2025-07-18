package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;

public interface ConjugationVariationService {
    /**
     * Return the ConjugationVariation related to a conjugationVerb, if is null the verb is regular
     *
     * @param conjugationVerb
     * @return Optional<ConjugationVariation>
     */
    ConjugationVariation getConjugationVariationByConjugationVerb(ConjugationVerb conjugationVerb);
}
