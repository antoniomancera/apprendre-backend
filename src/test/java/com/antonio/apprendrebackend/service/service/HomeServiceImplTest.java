package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckUserDTO;
import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.mapper.DeckUserMapper;
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
    private UserGoalService userGoalService;

    @Mock
    private DeckUserService deckUserService;

    @Mock
    private UserHistorialService userHistorialService;

    @Mock
    private StatsService statsService;

    @Mock
    private GoalMapper goalMapper;

    @Mock
    private DeckUserMapper deckUserMapper;

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
        UserGoal goalMock = new UserGoal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(userGoalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks
        DeckUser deckUser1 = new DeckUser();
        deckUser1.setId(1);
        DeckUser deckUser2 = new DeckUser();
        deckUser2.setId(2);
        List<DeckUser> decksMock = Arrays.asList(deckUser1, deckUser2);

        DeckUserDTO deckUserDTO1 = new DeckUserDTO();
        deckUserDTO1.setId(1);
        DeckUserDTO deckUserDTO2 = new DeckUserDTO();
        deckUserDTO2.setId(2);

        when(deckUserService.getActiveDecks(userInfo)).thenReturn(decksMock);
        when(deckUserMapper.toDTO(deckUser1)).thenReturn(deckUserDTO1);
        when(deckUserMapper.toDTO(deckUser2)).thenReturn(deckUserDTO2);

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
        verify(userGoalService, times(1)).getActiveGoal(userInfo);
        verify(deckUserService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckUserMapper, times(1)).toDTO(deckUser1);
        verify(deckUserMapper, times(1)).toDTO(deckUser2);
    }

    @Test
    void testGetHome_WithEmptyDecks() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        UserGoal goalMock = new UserGoal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(userGoalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks (empty)
        when(deckUserService.getActiveDecks(userInfo)).thenReturn(Collections.emptyList());

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
        verify(userGoalService, times(1)).getActiveGoal(userInfo);
        verify(deckUserService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckUserMapper, never()).toDTO(any());
    }

    @Test
    void testGetHome_WithNullDecks() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        UserGoal goalMock = new UserGoal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(userGoalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks (null)
        when(deckUserService.getActiveDecks(userInfo)).thenReturn(null);

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
        verify(userGoalService, times(1)).getActiveGoal(userInfo);
        verify(deckUserService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckUserMapper, never()).toDTO(any());
    }

    @Test
    void testGetHome_WithNoLastHistorial() {
        // Given
        // Setup Stats
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        // Setup Goal
        UserGoal goalMock = new UserGoal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(userGoalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        // Setup Decks
        DeckUser deckUser1 = new DeckUser();
        deckUser1.setId(1);
        List<DeckUser> decksMock = List.of(deckUser1);

        DeckUserDTO deckUserDTO1 = new DeckUserDTO();
        deckUserDTO1.setId(1);

        when(deckUserService.getActiveDecks(userInfo)).thenReturn(decksMock);
        when(deckUserMapper.toDTO(deckUser1)).thenReturn(deckUserDTO1);

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
        verify(userGoalService, times(1)).getActiveGoal(userInfo);
        verify(deckUserService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckUserMapper, times(1)).toDTO(deckUser1);
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
