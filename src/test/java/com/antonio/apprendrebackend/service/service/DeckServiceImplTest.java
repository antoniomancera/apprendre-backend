package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.Deck;
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
        Deck deck1 = new Deck();
        deck1.setId(1);
        deck1.setName("Deck 1");

        Deck deck2 = new Deck();
        deck2.setId(2);
        deck2.setName("Deck 2");

        List<Deck> activeDecks = Arrays.asList(deck1, deck2);

        // When
        when(deckRepository.findByEndDateNull()).thenReturn(activeDecks);

        List<Deck> result = deckService.getActiveDecks();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Deck 1", result.get(0).getName());
        assertEquals("Deck 2", result.get(1).getName());

        verify(deckRepository, times(1)).findByEndDateNull();
    }

    @Test
    void testGetActiveDecksEmpty() {
        // Given
        when(deckRepository.findByEndDateNull()).thenReturn(Arrays.asList());

        // When
        List<Deck> result = deckService.getActiveDecks();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(deckRepository, times(1)).findByEndDateNull();
    }
}
