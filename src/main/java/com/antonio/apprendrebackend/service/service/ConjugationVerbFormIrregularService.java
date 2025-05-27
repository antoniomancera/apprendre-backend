package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;

import java.util.List;

public interface ConjugationVerbFormIrregularService {
    /**
     * Given a conjugationVariation return the lit of irregularForms related
     *
     * @param conjugationVariation
     * @return
     */
    List<ConjugationVerbFormIrregular> findByConjugationVariation(ConjugationVariation conjugationVariation);
}
