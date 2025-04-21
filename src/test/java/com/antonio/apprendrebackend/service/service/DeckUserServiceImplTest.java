package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.DeckUser;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.repository.DeckUserRepository;
import com.antonio.apprendrebackend.service.service.impl.DeckUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class DeckUserServiceImplTest {

    @Mock
    private DeckUserRepository deckUserRepository;

    @InjectMocks
    private DeckUserServiceImpl deckService;

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

        DeckUser deckUser1 = new DeckUser();
        deckUser1.setId(1);
        deckUser1.setName("Deck 1");
        deckUser1.setUserInfo(userInfo);

        DeckUser deckUser2 = new DeckUser();
        deckUser2.setId(2);
        deckUser2.setName("Deck 2");
        deckUser2.setUserInfo(userInfo);

        List<DeckUser> activeDeckUsers = Arrays.asList(deckUser1, deckUser2);

        // When
        when(deckUserRepository.findByEndDateNullAndUserInfo(userInfo)).thenReturn(activeDeckUsers);
        List<DeckUser> result = deckService.getActiveDecks(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Deck 1", result.get(0).getName());
        assertEquals("Deck 2", result.get(1).getName());
        verify(deckUserRepository, times(1)).findByEndDateNullAndUserInfo(userInfo);
    }

    @Test
    void testGetActiveDecksEmpty() {
        // Given
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        // When
        when(deckUserRepository.findByEndDateNullAndUserInfo(userInfo)).thenReturn(Arrays.asList());
        List<DeckUser> result = deckService.getActiveDecks(userInfo);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckUserRepository, times(1)).findByEndDateNullAndUserInfo(userInfo);
    }

    @Test
    void testGetActiveDecksNullUserInfo() {
        // Given
        UserInfo userInfo = null;

        // When
        when(deckUserRepository.findByEndDateNullAndUserInfo(userInfo)).thenReturn(Arrays.asList());
        List<DeckUser> result = deckService.getActiveDecks(userInfo);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deckUserRepository, times(1)).findByEndDateNullAndUserInfo(userInfo);
    }
}
