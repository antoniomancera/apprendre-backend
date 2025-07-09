package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationNonExist;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;

import java.util.List;

public interface ConjugationNonExistService {
    /**
     * Get a list of ConjugationNonxist given a tense and a list of personGenderNumber
     *
     * @param tense
     * @param personGenderNumberEnums
     * @return List<ConjugationNonExist>
     */
    List<ConjugationNonExist> getByConjugationNonExistByTenseAndPersonGenderNumber(
            Tense tense,
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums
    );
}
