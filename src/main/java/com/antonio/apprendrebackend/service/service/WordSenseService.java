package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.model.WordSense;

import java.util.List;

public interface WordSenseService {
    /**
     * Get the wordSens object by their id
     *
     * @param wordSenseId
     * @return WordSense
     * @throws WordSenseNotFoundException
     */
    WordSense getById(Integer wordSenseId);

    /**
     * Get the WordSenses related to a word
     *
     * @param wordId
     * @return List<WordSense>
     */
    List<WordSense> getWordSensesByWordId(Integer wordId);
}
