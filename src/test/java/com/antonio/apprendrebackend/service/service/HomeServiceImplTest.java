package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.mapper.DeckMapper;
import com.antonio.apprendrebackend.service.mapper.GoalMapper;
import com.antonio.apprendrebackend.service.mapper.UserInfoMapper;
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
    @Mock
    private UserInfoMapper userInfoMapper;

    private UserInfo userInfo;

    private Deck deck;
    private DeckDTO deckDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUserName("testUser");

        deck = new Deck();
        deck.setId(1);

        deckDTO = new DeckDTO();
        deckDTO.setId(1);
    }

    @Test
    void testGetHome_Success() {
        // Given
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

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

        UserHistorial lastHistorial = new UserHistorial();
        lastHistorial.setDeck(deck1);
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.of(lastHistorial));

        // When
        Home result = homeService.getHome(userInfo);
        result.setLastDeck(deckDTO);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertEquals(2, result.getDecks().size());
        assertEquals(deckDTO, result.getLastDeck());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, times(2)).toDTO(deck1);
        verify(deckMapper, times(1)).toDTO(deck2);
    }

    @Test
    void testGetHome_WithEmptyDecks() {
        // Given
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        when(deckService.getActiveDecks(userInfo)).thenReturn(Collections.emptyList());

        UserHistorial lastHistorial = new UserHistorial();
        lastHistorial.setDeck(deck);
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.of(lastHistorial));

        // When
        Home result = homeService.getHome(userInfo);
        result.setLastDeck(deckDTO);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertTrue(result.getDecks().isEmpty());
        assertEquals(deckDTO, result.getLastDeck());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, times(1)).toDTO(any());
    }

    @Test
    void testGetHome_WithNullDecks() {
        // Given
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        when(deckService.getActiveDecks(userInfo)).thenReturn(null);

        UserHistorial lastHistorial = new UserHistorial();
        lastHistorial.setDeck(deck);
        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.of(lastHistorial));

        // When
        Home result = homeService.getHome(userInfo);
        result.setLastDeck(deckDTO);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertTrue(result.getDecks().isEmpty());
        assertEquals(deckDTO, result.getLastDeck());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, times(1)).toDTO(any());
    }

    @Test
    void testGetHome_WithNoLastHistorial() {
        // Given
        List<DailyStats> weekStatsMock = Arrays.asList(new DailyStats(), new DailyStats());
        when(statsService.getDailyStatsLastWeek(userInfo)).thenReturn(weekStatsMock);

        Goal goalMock = new Goal();
        GoalDTO goalDTOMock = new GoalDTO();
        when(goalService.getActiveGoal(userInfo)).thenReturn(goalMock);
        when(goalMapper.toDTO(goalMock)).thenReturn(goalDTOMock);

        Deck deck1 = new Deck();
        deck1.setId(1);
        List<Deck> decksMock = List.of(deck1);

        DeckDTO deckDTO1 = new DeckDTO();
        deckDTO1.setId(1);

        when(deckService.getActiveDecks(userInfo)).thenReturn(decksMock);
        when(deckMapper.toDTO(deck1)).thenReturn(deckDTO1);

        when(userHistorialService.getLastUserHistorial(userInfo)).thenReturn(Optional.empty());

        // When
        Home result = homeService.getHome(userInfo);

        // Then
        assertNotNull(result);
        assertEquals(weekStatsMock, result.getWeekStats());
        assertEquals(goalDTOMock, result.getGoal());
        assertEquals(1, result.getDecks().size());
        assertNull(result.getLastDeck());

        verify(statsService, times(1)).getDailyStatsLastWeek(userInfo);
        verify(goalService, times(1)).getActiveGoal(userInfo);
        verify(deckService, times(1)).getActiveDecks(userInfo);
        verify(userHistorialService, times(1)).getLastUserHistorial(userInfo);
        verify(goalMapper, times(1)).toDTO(goalMock);
        verify(deckMapper, times(1)).toDTO(deck1);
    }
}
