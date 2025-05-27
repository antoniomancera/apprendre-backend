package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.WordSenseNotFoundException;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.repository.WordSenseRepository;
import com.antonio.apprendrebackend.service.service.WordSenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordSenseServiceImpl implements WordSenseService {
    @Autowired
    private WordSenseRepository wordSenseRepository;

    /**
     * Get the wordSens object by their id
     *
     * @param wordSenseId
     * @return WordSense
     * @throws WordSenseNotFoundException
     */
    @Override
    public WordSense getById(Integer wordSenseId) {
        return wordSenseRepository.findById(wordSenseId).orElseThrow(() -> new WordSenseNotFoundException(String.format("Not found WordSense with id %s", wordSenseId)));
    }
}
