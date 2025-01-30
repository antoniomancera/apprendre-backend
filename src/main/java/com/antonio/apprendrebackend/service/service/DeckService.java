package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Deck;

import java.util.List;

public interface DeckService {
    /**
     * Get all active decks
     *
     * @return List<Deck>
     */
    List<Deck> getActiveDecks();
}
