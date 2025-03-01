package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.DeckDTO;
import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.dto.UserInfoDTO;
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


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HomeServiceImplTest {


    @Mock
    private GoalService goalService;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private StatsService statsService;

    @Mock
    private DeckService deckService;

    @Mock
    private DeckWordTranslationHistorialService deckWordTranslationHistorialService;

    @Mock
    private GoalMapper goalMapper;

    @Mock
    private UserInfoMapper userInfoMapper;

    @Mock
    private DeckMapper deckMapper;

    @InjectMocks
    private HomeServiceImpl homeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHome() {
        //given
        List<DailyStats> dailyStatsList = Arrays.asList(
                new DailyStats(LocalDate.now().minusDays(1)),
                new DailyStats(LocalDate.now().minusDays(2)),
                new DailyStats(LocalDate.now().minusDays(3))
        );
        List<Deck> activeDeckList = Arrays.asList(
                new Deck(),
                new Deck(),
                new Deck()
        );
        when(statsService.getDailyStatsLastWeek()).thenReturn(dailyStatsList);
        when(goalService.getActiveGoal()).thenReturn(new Goal());
        when(userInfoService.getUserInfo()).thenReturn(new UserInfo());
        when(deckService.getActiveDecks()).thenReturn(activeDeckList);
        when(deckWordTranslationHistorialService.getLastWordTranslationHistorial()).thenReturn(Optional.of(new DeckWordTranslationHistorial()));
        when(goalMapper.toDTO(any())).thenReturn(new GoalDTO());
        when(userInfoMapper.toDTO(any())).thenReturn(new UserInfoDTO());
        when(deckMapper.toDTO(any(Deck.class))).thenReturn(new DeckDTO());

        //when
        Home home = homeService.getHome();

        //then
        assertNotNull(home);
        assertNotNull(home.getWeekStats());
        assertNotNull(home.getGoal());
        assertNotNull(home.getUserInfo());

        verify(statsService).getDailyStatsLastWeek();
        verify(goalService).getActiveGoal();
        verify(userInfoService).getUserInfo();
        verify(goalMapper).toDTO(any());
        verify(userInfoMapper).toDTO(any());
    }
}
