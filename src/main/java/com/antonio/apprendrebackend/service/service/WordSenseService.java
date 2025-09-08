package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.model.WordSense;
import org.springframework.data.jpa.domain.Specification;

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

    /**
     * Get the senses of a word given some Specification(filters), if all the specs are null, call the method
     * getWordSensesByWordId
     *
     * @param spec
     * @param wordId
     * @return List<WordSense>
     */
    List<WordSense> getWordSensesByWordIdWithSpecification(Specification spec, Integer wordId);

    /**
     * Get the senses of a word in a deck
     *
     * @param wordId
     * @param deckId
     * @return List<WordSense>
     */
    List<WordSense> getWordSensesByWordIdAndDeckId(Integer wordId, Integer deckId);

    /**
     * Get all the differents senses of every word in a deck
     *
     * @param deckId
     * @return List<WordSense>
     */
    List<WordSense> getTargetLanguageWordSensesByDeckId(Integer deckId);

}
