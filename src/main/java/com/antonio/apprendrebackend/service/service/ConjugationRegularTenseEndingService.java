package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationRegularTenseEnding;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.model.VerbGroup;

import java.util.List;

public interface ConjugationRegularTenseEndingService {
    /**
     * Given a tense a verbGroup and a list of PersonGenderNumber return the regular ending related to them
     *
     * @param tenseEnum
     * @param verbGroupEnum
     * @param personGenderNumberEnums
     * @return List<ConjugationRegularTenseEnding>
     */
    List<ConjugationRegularTenseEnding> getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(
            Tense.TenseEnum tenseEnum,
            VerbGroup.VerbGroupEnum verbGroupEnum,
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums
    );
}
