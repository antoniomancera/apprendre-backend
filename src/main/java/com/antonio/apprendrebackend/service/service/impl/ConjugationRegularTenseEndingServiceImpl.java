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
}
