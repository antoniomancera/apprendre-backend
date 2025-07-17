package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.exception.DeckNotFoundException;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.DeckRepository;
import com.antonio.apprendrebackend.service.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckServiceImpl implements DeckService {
    private static final Logger logger = LoggerFactory.getLogger(DeckServiceImpl.class);

    @Autowired
    DeckRepository deckRepository;

    /**
     * Get all active decks by user
     *
     * @return List<Deck>
     */
    @Override
    public List<Deck> getActiveDecks(UserInfo userInfo) {
        logger.debug("Getting all the active deck for logged user");

        return deckRepository.findByEndDateNullAndUserInfo(userInfo);
    }

    /**
     * Get a deck by their id
     *
     * @param deckId
     * @return Deck
     * @throws DeckNotFoundException if not found anyone with their id
     */
    @Override
    public Deck getDeckbyId(Integer deckId) {
        logger.debug("Getting the deck by id: %d", deckId);

        return deckRepository.findById(deckId).orElseThrow(() -> new DeckNotFoundException(String.format("Not found any deck with id: %s", deckId)));
    }
}
