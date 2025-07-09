package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.TenseNotFoundException;
import com.antonio.apprendrebackend.service.model.Language;
import com.antonio.apprendrebackend.service.model.Tense;
import com.antonio.apprendrebackend.service.repository.TenseRepository;
import com.antonio.apprendrebackend.service.service.TenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenseServiceImpl implements TenseService {
    @Autowired
    private TenseRepository tenseRepository;

    /**
     * Returns a list of tenses given a language
     *
     * @param language
     * @return List<Tense>
     * @throws TenseNotFoundException if any tense related to the language is found
     */
    @Override
    public List<Tense> getByLanguage(Language language) {
        List<Tense> tenses = tenseRepository.findByLanguage(language);
        if (tenses.isEmpty()) {
            throw new TenseNotFoundException(String.format("Not found any tense for the language %s", language.getName()));
        }
        return tenses;
    }
}
