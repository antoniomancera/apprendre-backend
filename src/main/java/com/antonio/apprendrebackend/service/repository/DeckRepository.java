package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends CrudRepository<Deck, Integer> {
    List<Deck> findByEndDateNullAndUserInfo(UserInfo userInfo);

    Optional<Deck> findById(Integer deckId);

    List<Deck> findByName(String name);
}
