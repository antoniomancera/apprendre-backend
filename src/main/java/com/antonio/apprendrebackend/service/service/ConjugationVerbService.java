package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.ConjugationTenseDTO;
import com.antonio.apprendrebackend.service.exception.ConjugationVerbNotFoundException;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;
import com.antonio.apprendrebackend.service.model.ConjugationVerbWordWordSense;
import com.antonio.apprendrebackend.service.model.WordSense;


import java.util.List;
import java.util.Optional;

public interface ConjugationVerbService {
    /**
     * Given a wordSense return a list with the conjugationComplete(regular and irregular) with of all the tenses
     *
     * @param wordSenseId
     * @return List<ConjugationTenseDTO>
     */
    List<ConjugationTenseDTO> getConjugationComplete(int wordSenseId);

    /**
     * Returns if exists the conjugationVerb of a word
     *
     * @param wordId
     * @return ConjugationVerb
     */
    ConjugationVerb getConjugationVerbByWordId(Integer wordId);

    /**
     * Returns if exists the conjugationVerb of a wordSense
     *
     * @param wordSenseId
     * @return ConjugationVerb
     */
    ConjugationVerb getConjugationVerbByWordSenseId(Integer wordSenseId);
}
