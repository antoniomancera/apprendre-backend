package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;

public interface ConjugationVerbCompoundStructureService {
    /**
     * Return the ConjugationVerbCompountStructure of a tense, if not exists the verb is simple
     *
     * @param tenseId
     * @return ConjugationVerbCompoundStructure
     */
    ConjugationVerbCompoundStructure getConjugationVerbCompoundStructureByTenseId(Integer tenseId);
}
