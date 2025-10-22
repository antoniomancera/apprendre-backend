package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.ConjugationTenseInfoDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationTenseDTO;
import com.antonio.apprendrebackend.service.dto.ConjugationVerbWithTensesInfoDTO;
import com.antonio.apprendrebackend.service.model.ConjugationVerb;


import java.util.List;

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

    /**
     * Given a wordSense return the structure general of every tense Conjugation and their irregulars
     *
     * @param wordSenseId
     * @return ConjugationVerbWithTensesInfoDTO
     */
    ConjugationVerbWithTensesInfoDTO getConjugationVerbWithTensesInfoDTOByWordSenseId(Integer wordSenseId);
}
