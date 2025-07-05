package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationTensePersonGenderNumber;
import com.antonio.apprendrebackend.service.repository.ConjugationTensePersonGenderNumberRepository;
import com.antonio.apprendrebackend.service.service.ConjugationTensePersonGenderNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjugationTensePersonGenderNumberServiceImpl implements ConjugationTensePersonGenderNumberService {
    @Autowired
    ConjugationTensePersonGenderNumberRepository conjugationTensePersonGenderNumberRepository;

    /**
     * Returns the ConjugationTensePersonGenderNumber of a tense
     *
     * @param tenseId
     * @return List<ConjugationTensePersonGenderNumber>
     */
    @Override
    public List<ConjugationTensePersonGenderNumber> getConjugationTensePersonGenderNumbersByTenseId(Integer tenseId) {
        return conjugationTensePersonGenderNumberRepository.findByTenseId(tenseId);
    }
}
