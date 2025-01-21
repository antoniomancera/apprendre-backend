package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.repository.DeckRepository;
import com.antonio.apprendrebackend.service.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckServiceImpl implements DeckService {
    @Autowired
    DeckRepository deckRepository;

    @Override
    public List<Deck> getActiveDecks() {
        return deckRepository.findByEndDateNull();
    }

    @Override
    public Deck findById(Integer deckId) {
        return deckRepository.findById(deckId);
    }
}
