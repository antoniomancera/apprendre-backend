package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.repository.WordSenseRepository;
import com.antonio.apprendrebackend.service.service.WordSenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordSenseServiceImpl implements WordSenseService {
    private static final Logger logger = LoggerFactory.getLogger(WordSenseServiceImpl.class);
    @Autowired
    private WordSenseRepository wordSenseRepository;

    /**
     * Get the wordSense object by their id
     *
     * @param wordSenseId
     * @return WordSense
     * @throws WordSenseNotFoundException
     */
    @Override
    public WordSense getById(Integer wordSenseId) {
        logger.debug(String.format("Get the wordSense with id: %d", wordSenseId));

        return wordSenseRepository.findById(wordSenseId).orElseThrow(() -> new WordSenseNotFoundException(String.format("Not found WordSense with id %s", wordSenseId)));
    }

    /**
     * Get the WordSenses related to a word
     *
     * @param wordId
     * @return List<WordSense>
     */
    @Override
    public List<WordSense> getWordSensesByWordId(Integer wordId) {
        logger.debug(String.format("Get the wordSense with for the word: %d", wordId));

        return wordSenseRepository.findByWordId(wordId);
    }
}
