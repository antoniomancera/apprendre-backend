package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;

import java.util.List;

public interface DeckService {
    /**
     * Get all active decks by user
     *
     * @return List<Deck>
     */
    List<Deck> getActiveDecks(UserInfo userInfo);
}
