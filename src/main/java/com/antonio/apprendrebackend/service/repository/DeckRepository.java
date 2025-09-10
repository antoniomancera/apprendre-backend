package com.antonio.apprendrebackend.service.repository;

import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends CrudRepository<Deck, Integer> {
    List<Deck> findByEndDateNullAndUserInfo(UserInfo userInfo);

    Optional<Deck> findById(Integer deckId);

    List<Deck> findByName(String name);

    /**
     * Count the decks already in use(endDate null) for a user
     *
     * @param userId
     * @return Integer
     */
    @Query("""
               SELECT COUNT(d) 
               FROM Deck d
               WHERE d.userInfo.id = :userId
               AND d.endDate IS NULL
            """)
    Integer countByUserIdAndEndDateNull(Integer userId);
}
