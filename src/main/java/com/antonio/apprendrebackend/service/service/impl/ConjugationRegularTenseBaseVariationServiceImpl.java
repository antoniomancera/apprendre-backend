package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.repository.ConjugationRegularTenseBaseVariationRepository;
import com.antonio.apprendrebackend.service.service.ConjugationRegularTenseBaseVariationService;
import com.antonio.apprendrebackend.service.service.ConjugationRegularTenseEndingService;
import com.antonio.apprendrebackend.service.service.ConjugationVariationService;
import com.antonio.apprendrebackend.service.service.ConjugationVerbFormIrregularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConjugationRegularTenseBaseVariationServiceImpl implements ConjugationRegularTenseBaseVariationService {
    @Autowired
    private ConjugationRegularTenseBaseVariationRepository conjugationRegularTenseBaseVariationRepository;
    @Autowired
    private ConjugationVerbFormIrregularService conjugationVerbFormIrregularService;
    @Autowired
    private ConjugationRegularTenseEndingService conjugationRegularTenseEndingService;
    @Autowired
    private ConjugationVariationService conjugationVariationService;

    /**
     * Method that return if exist any variation for the base in which a tense is conjugated
     *
     * @param tenseId
     * @return ConjugationRegularTenseBaseVariation
     */
    @Override
    public ConjugationRegularTenseBaseVariation getConjugationRegularTenseBaseVariationByTenseId(Integer tenseId) {
        Optional<ConjugationRegularTenseBaseVariation> conjugationRegularTenseBaseVariation = conjugationRegularTenseBaseVariationRepository.findByTenseId(tenseId);
        if (conjugationRegularTenseBaseVariation.isPresent()) {
            return conjugationRegularTenseBaseVariation.get();
        }
        return null;
    }

    /**
     * Returns the regular base for conjugation
     *
     * @param verb
     * @param conjugationVerb
     * @param tenseId
     * @return String
     */
    @Override
    public String getRegularTenseBase(WordSense verb, ConjugationVerb conjugationVerb, Integer tenseId) {
        ConjugationRegularTenseBaseVariation conjugationRegularTenseBaseVariation = getConjugationRegularTenseBaseVariationByTenseId(tenseId);
        if (conjugationRegularTenseBaseVariation != null) {
            if (conjugationRegularTenseBaseVariation.getIsInfinitiveBase()) {
                if (conjugationRegularTenseBaseVariation.getIsEndingInBase()) {
                    return verb.getWord().getName();
                }
            }
            if (conjugationRegularTenseBaseVariation.getConjugationVerbFormBase() != null) {
                ConjugationVariation conjugationVariation = conjugationVariationService.getConjugationVariationByConjugationVerb(conjugationVerb);
                ConjugationVerbFormIrregular conjugationVerbFormIrregular = conjugationVerbFormIrregularService.getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(conjugationVariation, conjugationRegularTenseBaseVariation.getConjugationVerbFormBase());
                if (conjugationVerbFormIrregular != null) {
                    if (conjugationRegularTenseBaseVariation.getIsEndingInBase()) {
                        return conjugationVerbFormIrregular.getName();
                    }
                    ConjugationRegularTenseEnding conjugationRegularTenseEnding = conjugationRegularTenseEndingService.getByTenseAndPersonGenderNumberAndVerbGroup(conjugationRegularTenseBaseVariation.getConjugationVerbFormBase().getTense().getId(), conjugationVerbFormIrregular.getConjugationVerbForm().getPersonGenderNumber().getPersonGenderNumberEnum(), conjugationVerb.getVerbGroupEnding().getVerbGroup().getId());
                    return removeEndingFromVerb(conjugationVerbFormIrregular.getName(), conjugationRegularTenseEnding.getEnding());
                }
            }
        }
        return removeEndingFromVerb(verb.getWord().getName(), conjugationVerb.getVerbGroupEnding().getVerbEnding().getName());
    }

    private String removeEndingFromVerb(String verbName, String ending) {
        int lastIndex = verbName.lastIndexOf(ending);
        if (lastIndex == -1) {
            return verbName;
        }
        return verbName.substring(0, lastIndex) + verbName.substring(lastIndex + ending.length());
    }
}
