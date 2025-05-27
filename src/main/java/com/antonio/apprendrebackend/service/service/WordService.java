package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.WordDTO;
import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;

import java.util.List;

public interface WordService {
    /**
     * Get all the words that are verbs
     *
     * @return List<WordDTO>
     * @throws TypeNotFoundException if not exist Verb as a type
     * @throws WordNotFoundException if not exist any Verb
     */
    List<WordDTO> getAllVerbs();
}