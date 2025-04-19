package com.antonio.apprendrebackend.service.service.impl;

import com.antonio.apprendrebackend.service.model.DeckUser;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.DeckUserRepository;
import com.antonio.apprendrebackend.service.service.DeckUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckUserServiceImpl implements DeckUserService {
    @Autowired
    DeckUserRepository deckUserRepository;

    /**
     * Get all active decks by user
     *
     * @return List<Deck>
     */
    @Override
    public List<DeckUser> getActiveDecks(UserInfo userInfo) {
        return deckUserRepository.findByEndDateNullAndUserInfo(userInfo);
    }
}
