package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;

import java.util.List;

public interface ConjugationVerbFormService {
    /**
     * Get all the ConjugationVerbForm of a tense an a list of personGenderNumbers
     *
     * @param tense
     * @param personGenderNumberEnums
     * @return List<ConjugationVerbForm>
     */
    List<ConjugationVerbForm> getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(Tense tense, List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums);
}
