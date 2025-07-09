package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbCompoundStructureRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVerbCompoundStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConjugationVerbCompoundStructureServiceImpl implements ConjugationVerbCompoundStructureService {
    @Autowired
    private ConjugationVerbCompoundStructureRepository conjugationVerbCompoundStructureRepository;

    /**
     * Return the ConjugationVerbCompountStructure of a tense, if not exists the verb is simple
     *
     * @param tenseId
     * @return ConjugationVerbCompoundStructure
     */
    @Override
    public ConjugationVerbCompoundStructure getConjugationVerbCompoundStructureByTenseId(Integer tenseId) {
        if (conjugationVerbCompoundStructureRepository.findByTenseId(tenseId).isPresent()) {
            return conjugationVerbCompoundStructureRepository.findByTenseId(tenseId).get();
        }
        return null;
    }
}
