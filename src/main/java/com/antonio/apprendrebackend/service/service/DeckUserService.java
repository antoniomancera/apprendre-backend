package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.DeckUser;
import com.antonio.apprendrebackend.service.model.UserInfo;

import java.util.List;

public interface DeckUserService {
    /**
     * Get all active decks by user
     *
     * @return List<Deck>
     */
    List<DeckUser> getActiveDecks(UserInfo userInfo);
}
