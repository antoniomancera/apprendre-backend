package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationVariation;
import com.antonio.apprendrebackend.service.model.ConjugationVerbForm;
import com.antonio.apprendrebackend.service.model.ConjugationVerbFormIrregular;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbFormIrregularRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVerbFormIrregularService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConjugationVerbFormIrregularServiceImpl implements ConjugationVerbFormIrregularService {
    private static final Logger logger = LoggerFactory.getLogger(ConjugationVerbFormIrregularServiceImpl.class);

    @Autowired
    private ConjugationVerbFormIrregularRepository conjugationVerbFormIrregularRepository;

    /**
     * Given a conjugationVariation return the lit of irregularForms related
     *
     * @param conjugationVariation
     * @return
     */
    @Override
    public List<ConjugationVerbFormIrregular> getConjugationVerbFormIrregularsByConjugationVariation(ConjugationVariation conjugationVariation) {
        logger.debug("Called getConjugationVerbFormIrregularsByConjugationVariation in ConjugationVerbFormIrregularService for conjugationVariation-{}", conjugationVariation.getId());

        return conjugationVerbFormIrregularRepository.findByConjugationVariation(conjugationVariation);
    }

    /**
     * Return if exist the conjugationVerbFormIrregular given a conjugationVariation and a conjugationVerbForm
     *
     * @param conjugationVariation
     * @param conjugationVerbForm
     * @return ConjugationVerbFormIrregular
     */
    @Override
    public ConjugationVerbFormIrregular getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm(ConjugationVariation conjugationVariation, ConjugationVerbForm conjugationVerbForm) {
        logger.debug("Called getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm in ConjugationVerbFormIrregularService for conjugationVariation-{}, and conjugationVerbForm-{}", conjugationVariation.getId(), conjugationVerbForm.getId());

        Optional<ConjugationVerbFormIrregular> conjugationVerbFormIrregularOptional = conjugationVerbFormIrregularRepository.findByConjugationVariationAndConjugationVerbForm(conjugationVariation, conjugationVerbForm);
        if (conjugationVerbFormIrregularOptional.isPresent()) {
            return conjugationVerbFormIrregularOptional.get();
        }
        return null;
    }

    /**
     * Return the Irregular forms given a list of conjugationVerbForms
     *
     * @param conjugationVariation
     * @param conjugationVerbForms
     * @return List<ConjugationVerbFormIrregular>
     */
    @Override
    public List<ConjugationVerbFormIrregular> getConjugationVerbFormIrregularsByConjugationVariationAndConjugationVerbForms(ConjugationVariation conjugationVariation, List<ConjugationVerbForm> conjugationVerbForms) {
        logger.debug("Called getConjugationVerbFormIrregularByConjugationVariationAndConjugationVerbForm in ConjugationVerbFormIrregularService for conjugationVariation-{}, and conjugationVerbForms-{}", conjugationVariation.getId(), conjugationVerbForms.size());

        return conjugationVerbFormIrregularRepository.findByConjugationVariationAndConjugationVerbFormIn(conjugationVariation, conjugationVerbForms);
    }
}
