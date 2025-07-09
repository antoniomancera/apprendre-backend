package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.mapper.GoalMapper;
import com.antonio.apprendrebackend.service.model.*;
import com.antonio.apprendrebackend.service.service.impl.HomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeServiceImplTest {
    @InjectMocks
    private HomeServiceImpl homeService;

    @Mock
    private GoalService goalService;

    @Mock
    private DeckService deckService;

    @Mock
    private UserHistorialService userHistorialService;

    @Mock
    private StatsService statsService;

    @Mock
    private GoalMapper goalMapper;

    @Mock
    private DeckMapper deckMapper;

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");
    }

    @Test
    void testGetHome_Success() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks
        Deck deck1 = new Deck();
        deck1.setId(1);
        Deck deck2 = new Deck();
        deck2.setId(2);
        List<Deck> decksMock = Arrays.asList(deck1, deck2);

        DeckDTO deckDTO1 = new DeckDTO();
        deckDTO1.setId(1);
        DeckDTO deckDTO2 = new DeckDTO();
        deckDTO2.setId(2);

        when(deckService.getActiveDecks(userInfo)).thenReturn(decksMock);
        when(deckMapper.toDTO(deck1)).thenReturn(deckDTO1);
        when(deckMapper.toDTO(deck2)).thenReturn(deckDTO2);

        // Setup Last Deck
        UserHistorial lastHistorial = new UserHistorial();
        lastHistorial.setDeckId(1);
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.of(lastHistorial));

        // When
        Home result = homeService.getHome(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertEquals(2, result.getDecks().size());
        assertEquals(1, result.getLastDeckId());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, times(1)).toDTO(deck1);
        verify(deckMapper, times(1)).toDTO(deck2);
    }

    @Test
    void testGetHome_WithEmptyDecks() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks (empty)
        when(deckService.getActiveDecks(userInfo)).thenReturn(Collections.emptyList());

        // Setup Last Deck
        UserHistorial lastHistorial = new UserHistorial();
        lastHistorial.setDeckId(1);
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.of(lastHistorial));

        // When
        Home result = homeService.getHome(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertTrue(result.getDecks().isEmpty());
        assertEquals(1, result.getLastDeckId());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, never()).toDTO(any());
    }

    @Test
    void testGetHome_WithNullDecks() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks (null)
        when(deckService.getActiveDecks(userInfo)).thenReturn(null);

        // Setup Last Deck
        UserHistorial lastHistorial = new UserHistorial();
        lastHistorial.setDeckId(1);
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.of(lastHistorial));

        // When
        Home result = homeService.getHome(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertTrue(result.getDecks().isEmpty());
        assertEquals(1, result.getLastDeckId());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, never()).toDTO(any());
    }

    @Test
    void testGetHome_WithNoLastHistorial() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks
        Deck deck1 = new Deck();
        deck1.setId(1);
        List<Deck> decksMock = List.of(deck1);

        DeckDTO deckDTO1 = new DeckDTO();
        deckDTO1.setId(1);

        when(deckService.getActiveDecks(userInfo)).thenReturn(decksMock);
        when(deckMapper.toDTO(deck1)).thenReturn(deckDTO1);

        // Setup Last Deck (empty)
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.empty());

        // When
        Home result = homeService.getHome(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertEquals(1, result.getDecks().size());
        assertNull(result.getLastDeckId());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, times(1)).toDTO(deck1);
    }

    @Test
    void testGetHome_WithNullUserInfo() {
      /*  // Given
        UserInfo nullUserInfo = null;

        // When
        Exception exception = assertThrows(NullPointerException.class, () -> {
            homeService.getHome(nullUserInfo);
        });

        // Then
        assertNotNull(exception);

        // Verify that none of the services was called
        verify(statsService, never()).getDailyStatsLastWeek(any());
        verify(userGoalService, never()).getActiveGoal(any());
        verify(deckUserService, never()).getActiveDecks(any());
        verify(userHistorialService, never()).getLastUserHistorial(any());

       */
    }
}
