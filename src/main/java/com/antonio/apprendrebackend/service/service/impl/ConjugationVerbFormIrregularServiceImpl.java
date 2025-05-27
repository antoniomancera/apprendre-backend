package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbFormIrregularRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVerbFormIrregularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConjugationVerbFormIrregularServiceImpl implements ConjugationVerbFormIrregularService {
    @Autowired
    private ConjugationVerbFormIrregularRepository conjugationVerbFormIrregularRepository;

    /**
     * Given a conjugationVariation return the lit of irregularForms related
     *
     * @param conjugationVariation
     * @return
     */
    @Override
    public List<ConjugationVerbFormIrregular> findByConjugationVariation(ConjugationVariation conjugationVariation) {
        return conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation);
    }
}
