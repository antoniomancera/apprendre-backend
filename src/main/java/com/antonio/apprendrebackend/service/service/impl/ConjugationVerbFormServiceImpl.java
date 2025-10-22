package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.PersonGenderNumber;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbFormRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVerbFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjugationVerbFormServiceImpl implements ConjugationVerbFormService {
    private static final Logger logger = LoggerFactory.getLogger(ConjugationVerbFormService.class);
    @Autowired
    private ConjugationVerbFormRepository conjugationVerbFormRepository;

    /**
     * Get all the ConjugationVerbForm of a tense an a list of personGenderNumbers
     *
     * @param tense
     * @param personGenderNumberEnums
     * @return List<ConjugationVerbForm>
     */
    @Override
    public List<ConjugationVerbForm> getConjugationVerbFormsByTenseAndPersonGenderNumberEnums(Tense tense, List<PersonGenderNumber.PersonGenderNumberEnum> personGenderNumberEnums) {
        logger.debug("Called getConjugationVerbFormsByTenseAndPersonGenderNumberEnums in ConjugationVerbFormService for tense-{}, and personGenderNumberEnums-{}", tense.getId(), personGenderNumberEnums.size());

        return conjugationVerbFormRepository.findByTenseAndPersonGenderNumberPersonGenderNumberEnumIn(tense, personGenderNumberEnums);
    }
}
