package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationRegularTenseEnding;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.model.VerbGroup;
import com.antonio.apprendrebackend.service.repository.ConjugationRegularTenseEndingRepository;
import com.antonio.apprendrebackend.service.service.ConjugationRegularTenseEndingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConjugationRegularTenseEndingServiceImpl implements ConjugationRegularTenseEndingService {
    @Autowired
    private ConjugationRegularTenseEndingRepository conjugationRegularTenseEndingRepository;

    /**
     * Given a tense a verbGroup and a list of PersonGenderNumber return the regular ending related to them
     *
     * @param tenseEnum
     * @param verbGroupEnum
     * @param personGenderNumberEnums
     * @return List<ConjugationRegularTenseEnding>
     */
    @Override
    public List<ConjugationRegularTenseEnding> getByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(Tense.TenseEnum tenseEnum, VerbGroup.VerbGroupEnum verbGroupEnum, List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums) {
        return conjugationRegularTenseEndingRepository.findByTenseTenseEnumAndVerbGroupVerbGroupEnumAndPersonGenderNumberPersonGenderNumberEnumIn(tenseEnum, verbGroupEnum, personGenderNumberEnums);
    }

    /**
     * Returns a list with the regular endings of a tense of a verbGroup given a list of personGenderNumbers, if not exists try to search if exist some ConjugationRegularTenseEndings
     * not related to a verbGroup, that is, an ending for every verbGroup
     *
     * @param tenseId
     * @param personGenderNumberEnums
     * @param verbGroupId
     * @return List<ConjugationRegularTenseEnding>
     */
    @Override
    public List<ConjugationRegularTenseEnding> getByTenseInPersonGenderNumberAndVerbGroup(
            Integer tenseId,
            List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums,
            Integer verbGroupId
    ) {
        List<ConjugationRegularTenseEnding> conjugationRegularTenseEndings = conjugationRegularTenseEndingRepository.getByTenseInPersonGenderNumberEnumAndVerbGroup(
                tenseId,
                personGenderNumberEnums,
                verbGroupId);
        if (conjugationRegularTenseEndings.size() > 0) {
            return conjugationRegularTenseEndings;
        }

        conjugationRegularTenseEndings = conjugationRegularTenseEndingRepository.getByTenseInPersonGenderNumberEnum(
                tenseId,
                personGenderNumberEnums
        );
        if (conjugationRegularTenseEndings.size() > 0) {
            return conjugationRegularTenseEndings;
        }

        return null;
    }

    /**
     * Returns a list with the regular endings of a tense of a verbGroup given a personGenderNumber, if not exists try to search if exist a ConjugationRegularTenseEnding
     * not related to a verbGroup, that is, an ending for every verbGroup
     *
     * @param tenseId
     * @param personGenderNumberEnum
     * @param verbGroupId
     * @return ConjugationRegularTenseEnding
     */
    @Override
    public ConjugationRegularTenseEnding getByTenseAndPersonGenderNumberAndVerbGroup(Integer tenseId, PersonGenderNumber.PersonGenderNumberEnum personGenderNumberEnum, Integer verbGroupId) {
        Optional<ConjugationRegularTenseEnding> conjugationRegularTenseEndingOptional = conjugationRegularTenseEndingRepository.getByTenseAndPersonGenderNumberEnumAndVerbGroup(
                tenseId,
                personGenderNumberEnum,
                verbGroupId);
        if (conjugationRegularTenseEndingOptional.isPresent()) {
            return conjugationRegularTenseEndingOptional.get();
        }

        conjugationRegularTenseEndingOptional = conjugationRegularTenseEndingRepository.getByTenseAndPersonGenderNumberEnum(
                tenseId,
                personGenderNumberEnum
        );
        if (conjugationRegularTenseEndingOptional.isPresent()) {
            return conjugationRegularTenseEndingOptional.get();
        }

        return null;
    }
}
