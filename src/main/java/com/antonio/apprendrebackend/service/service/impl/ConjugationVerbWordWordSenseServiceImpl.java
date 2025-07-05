package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.ConjugationVerbWordWordSense;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbWordWordSenseRepository;
import com.antonio.apprendrebackend.service.service.ConjugationVerbWordWordSenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConjugationVerbWordWordSenseServiceImpl implements ConjugationVerbWordWordSenseService {
    @Autowired
    private ConjugationVerbWordWordSenseRepository conjugationVerbWordWordSenseRepository;

    /**
     * Get the ConjugationVerbWordWordSense of a wordSenseId
     *
     * @param wordSenseId
     * @return Optional<ConjugationVerbWordWordSense>
     */
    @Override
    public Optional<ConjugationVerbWordWordSense> getConjugationVerbWordWordSenseByWordSenseId(Integer wordSenseId) {
        return conjugationVerbWordWordSenseRepository.findByWordSenseId(wordSenseId);
    }

    /**
     * Get the ConjugationVerbWordWordSense of a wordId
     *
     * @param wordId
     * @return Optional<ConjugationVerbWordWordSense>
     */
    @Override
    public Optional<ConjugationVerbWordWordSense> getConjugationVerbWordWordSenseByWordId(Integer wordId) {
        return conjugationVerbWordWordSenseRepository.findByWordId(wordId);
    }
}
