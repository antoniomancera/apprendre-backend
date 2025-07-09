package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbWordWordSense;

import java.util.Optional;

public interface ConjugationVerbWordWordSenseService {
    /**
     * Get the ConjugationVerbWordWordSense of a wordSenseId
     *
     * @param wordSenseId
     * @return Optional<ConjugationVerbWordWordSense>
     */
    Optional<ConjugationVerbWordWordSense> getConjugationVerbWordWordSenseByWordSenseId(Integer wordSenseId);

    /**
     * Get the ConjugationVerbWordWordSense of a wordId
     *
     * @param wordId
     * @return Optional<ConjugationVerbWordWordSense>
     */
    Optional<ConjugationVerbWordWordSense> getConjugationVerbWordWordSenseByWordId(Integer wordId);
}
