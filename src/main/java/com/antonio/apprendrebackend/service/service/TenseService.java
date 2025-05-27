package com.antonio.apprendrebackend.service.service;

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
}
