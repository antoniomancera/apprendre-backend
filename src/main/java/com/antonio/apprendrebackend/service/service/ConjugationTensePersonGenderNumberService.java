package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationTensePersonGenderNumber;

import java.util.List;

public interface ConjugationTensePersonGenderNumberService {
    /**
     * Returns the ConjugationTensePersonGenderNumber of a tense
     *
     * @param tenseId
     * @return List<ConjugationTensePersonGenderNumber>
     */
    List<ConjugationTensePersonGenderNumber> getConjugationTensePersonGenderNumbersByTenseId(Integer tenseId);
}
