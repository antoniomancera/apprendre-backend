package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.exception.DeckAlreadyExistsException;
import com.antonio.apprendrebackend.service.model.Deck;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.DeckRepository;
import com.antonio.apprendrebackend.service.service.impl.DeckServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class DeckServiceImplTest {

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private DeckServiceImpl deckService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetActiveDecks() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Deck deck1 = new Deck();
        deck1.setId(1);
        deck1.setName("Deck 1");
        deck1.setUserInfo(userInfo);

        Deck deck2 = new Deck();
        deck2.setId(2);
        deck2.setName("Deck 2");
        deck2.setUserInfo(userInfo);

        List<Deck> activeDecks = Arrays.asList(deck1, deck2);

        // When
        when(deckRepository.findByEndDateNullAndUserInfo(userInfo)).thenReturn(activeDecks);
        List<Deck> result = deckService.getActiveDecks(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Deck 1", result.get(0).getName());
        assertEquals("Deck 2", result.get(1).getName());
        verify(deckRepository, times(1)).findByEndDateNullAndUserInfo(userInfo);
    }

    @Test
    void testGetActiveDecksEmpty() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        // When
        when(deckRepository.findByEndDateNullAndUserInfo(userInfo)).thenReturn(Arrays.asList());
        List<Deck> result = deckService.getActiveDecks(userInfo);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckRepository, times(1)).findByEndDateNullAndUserInfo(userInfo);
    }

    @Test
    void testGetActiveDecksNullUserInfo() {
        // Given
        UserInfo userInfo = null;

        // When
        when(deckRepository.findByEndDateNullAndUserInfo(userInfo)).thenReturn(Arrays.asList());
        List<Deck> result = deckService.getActiveDecks(userInfo);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckRepository, times(1)).findByEndDateNullAndUserInfo(userInfo);
    }

    @Test
    void testCreateDeckSuccess() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Deck newDeck = new Deck();
        newDeck.setName("New Deck");
        newDeck.setUserInfo(userInfo);

        Deck savedDeck = new Deck();
        savedDeck.setId(1);
        savedDeck.setName("New Deck");
        savedDeck.setUserInfo(userInfo);

        // When
        when(deckRepository.findByName("New Deck")).thenReturn(Arrays.asList());
        when(deckRepository.save(newDeck)).thenReturn(savedDeck);

        Deck result = deckService.createDeck(newDeck);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Deck", result.getName());
        assertEquals(userInfo, result.getUserInfo());
        verify(deckRepository, times(1)).findByName("New Deck");
        verify(deckRepository, times(1)).save(newDeck);
    }

    @Test
    void testCreateDeckWhenNameAlreadyExists() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Deck newDeck = new Deck();
        newDeck.setName("Existing Deck");
        newDeck.setUserInfo(userInfo);

        Deck existingDeck = new Deck();
        existingDeck.setId(1);
        existingDeck.setName("Existing Deck");
        existingDeck.setUserInfo(userInfo);

        List<Deck> existingDecks = Arrays.asList(existingDeck);

        // When
        when(deckRepository.findByName("Existing Deck")).thenReturn(existingDecks);

        // Then
        DeckAlreadyExistsException exception = assertThrows(
                DeckAlreadyExistsException.class,
                () -> deckService.createDeck(newDeck)
        );

        assertEquals("A deck with name: Existing Deck already exists", exception.getMessage());
        verify(deckRepository, times(1)).findByName("Existing Deck");
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void testCreateDeckWhenFindByNameReturnsNull() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        Deck newDeck = new Deck();
        newDeck.setName("New Deck");
        newDeck.setUserInfo(userInfo);

        Deck savedDeck = new Deck();
        savedDeck.setId(1);
        savedDeck.setName("New Deck");
        savedDeck.setUserInfo(userInfo);

        // When
        when(deckRepository.findByName("New Deck")).thenReturn(null);
        when(deckRepository.save(newDeck)).thenReturn(savedDeck);

        Deck result = deckService.createDeck(newDeck);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Deck", result.getName());
        verify(deckRepository, times(1)).findByName("New Deck");
        verify(deckRepository, times(1)).save(newDeck);
    }
}
