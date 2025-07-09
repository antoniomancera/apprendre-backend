package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;

import java.util.List;

public interface ConjugationVerbFormIrregularService {
    /**
     * Given a conjugationVariation return the lit of irregularForms related
     *
     * @param conjugationVariation
     * @return List<ConjugationVerbFormIrregular>
     */
    List<ConjugationVerbFormIrregular> getConjugationVerbFormIrregularsByConjugationVariation(ConjugationVariation conjugationVariation);

    /**
     * Return if exist the conjugationVerbFormIrregular given a conjugationVariation and a conjugationVerbForm
     *
     * @param conjugationVariation
     * @param conjugationVerbForm
     * @return ConjugationVerbFormIrregular
     */
    ConjugationVerbFormIrregular getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(ConjugationVariation conjugationVariation, ConjugationVerbForm conjugationVerbForm);

}
