package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.ConjugationVariationFoundException;
import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;
import com.antonio.apprendrebackend.service.repository.ConjugationVariationRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConjugationVariationServiceImpl implements ConjugationVariationService {
    @Autowired
    private ConjugationVariationRepository conjugationVariationRepository;

    /**
     * Return the ConjugationVariation related to a conjugationVerb, if is null the verb is regular
     *
     * @param conjugationVerb
     * @return Optional<ConjugationVariation>
     */
    @Override
    public ConjugationVariation getConjugationVariationByConjugationVerb(ConjugationVerb conjugationVerb) {
        return conjugationVariationRepository.findByConjugationVerb(conjugationVerb).orElseThrow(() -> new ConjugationVariationFoundException(String.format("Not found any COnjugation variation for Conjugationverb %s", conjugationVerb.getId())));
    }
}
