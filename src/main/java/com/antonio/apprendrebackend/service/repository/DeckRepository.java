package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeckRepository extends CrudRepository<Deck, Integer> {
    List<Deck> findByEndDateNullAndUserInfo(UserInfo userInfo);
}
