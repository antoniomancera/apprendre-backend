package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationNonExist;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.ConjugationNonExistRepository;
import com.antonio.apprendrebackend.service.service.ConjugationNonExistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjugationNonExistServiceImpl implements ConjugationNonExistService {
    @Autowired
    private ConjugationNonExistRepository conjugationNonExistRepository;
    

    /**
     * Get a list of ConjugationNonxist given a tense and a list of personGenderNumber
     *
     * @param tense
     * @param personGenderNumberEnums
     * @return List<ConjugationNonExist>
     */
    @Override
    public List<ConjugationNonExist> getByConjugationNonExistByTenseAndPersonGenderNumber(Tense tense, List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums) {
        return conjugationNonExistRepository.findByConjugationVerbForm_TenseAndConjugationVerbForm_PersonGenderNumber_PersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }
}
