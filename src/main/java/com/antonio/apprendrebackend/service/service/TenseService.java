package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.MoodWithTenseDTO;
import com.antonio.apprendrebackend.service.exception.TenseNotFoundException;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Tense;

import java.util.List;

public interface TenseService {
    /**
     * Returns a list of tenses given a language
     *
     * @param language
     * @return List<Tense>
     * @throws TenseNotFoundException if any tense related to the language is found
     */
    List<Tense> getByLanguage(Language language);

    /**
     * Returns all the tenses in the database
     *
     * @return List<Tense>
     */
    List<Tense> getAllTenses();

    /**
     * Returns all the moods with their tenses, all the tenses without a mood are included
     * in a false mood with the id -1. Furthermore, the tenses did not contains their mood, in
     * order to avoid repeat information
     *
     * @return List<MoodWithTenseDTO>
     */
    List<MoodWithTenseDTO> getAllMoodWithTense();
}
