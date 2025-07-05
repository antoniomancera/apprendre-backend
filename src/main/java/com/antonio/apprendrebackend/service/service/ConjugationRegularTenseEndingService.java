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

    /**
     * Returns a list with the regular endings of a tense of a verbGroup given a list of personGenderNumbers, if not exists try to search if exist some ConjugationRegularTenseEndings
     * not related to a verbGroup, that is, an ending for every verbGroup
     *
     * @param tenseId
     * @param personGenderNumberEnums
     * @param verbGroupId
     * @return List<ConjugationRegularTenseEnding>
     */
    List<ConjugationRegularTenseEnding> getByTenseInPersonGenderNumberAndVerbGroup(
            Integer tenseId,
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums,
            Integer verbGroupId
    );

    /**
     * Returns a list with the regular endings of a tense of a verbGroup given a personGenderNumber, if not exists try to search if exist a ConjugationRegularTenseEnding
     * not related to a verbGroup, that is, an ending for every verbGroup
     *
     * @param tenseId
     * @param personGenderNumberEnum
     * @param verbGroupId
     * @return ConjugationRegularTenseEnding
     */
    ConjugationRegularTenseEnding getByTenseAndPersonGenderNumberAndVerbGroup(
            Integer tenseId,
            PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum,
            Integer verbGroupId
    );
}
