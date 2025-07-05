package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.*;


public interface ConjugationRegularTenseBaseVariationService {
    /**
     * Method that return if exist any variation for the base in which a tense is conjugated
     *
     * @param tenseId
     * @return ConjugationRegularTenseBaseVariation
     */
    ConjugationRegularTenseBaseVariation getConjugationRegularTenseBaseVariationByTenseId(Integer tenseId);

    /**
     * Returns the regular base for conjugation
     *
     * @param verb
     * @param conjugationVerb
     * @param tenseId
     * @return String
     */
    String getRegularTenseBase(WordSense verb, ConjugationVerb conjugationVerb, Integer tenseId);
}
