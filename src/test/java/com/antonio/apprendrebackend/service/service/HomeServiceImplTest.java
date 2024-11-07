package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.dto.GoalDTO;
import com.antonio.apprendrebackend.service.dto.UserInfoDTO;
import com.antonio.apprendrebackend.service.mapper.GoalMapper;
import com.antonio.apprendrebackend.service.mapper.UserInfoMapper;
import com.antonio.apprendrebackend.service.model.DailyStats;
import com.antonio.apprendrebackend.service.model.Goal;
import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.service.impl.HomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HomeServiceImplTest {

    @Mock
    private DailyStatsService dailyStatsService;

    @Mock
    private GoalService goalService;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private GoalMapper goalMapper;

    @Mock
    private UserInfoMapper userInfoMapper;

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
        when(dailyStatsService.getDailyStatsLastWeek()).thenReturn(dailyStatsList);
        when(goalService.getActiveGoal()).thenReturn(new Goal());
        when(userInfoService.getUserInfo()).thenReturn(new UserInfo());

        when(goalMapper.toDTO(any())).thenReturn(new GoalDTO());
        when(userInfoMapper.toDTO(any())).thenReturn(new UserInfoDTO());

        //when
        Home home = homeService.getHome();

        //then
        assertNotNull(home);
        assertNotNull(home.getWeekStats());
        assertNotNull(home.getGoal());
        assertNotNull(home.getUserInfo());

        verify(dailyStatsService).getDailyStatsLastWeek();
        verify(goalService).getActiveGoal();
        verify(userInfoService).getUserInfo();
        verify(goalMapper).toDTO(any());
        verify(userInfoMapper).toDTO(any());
    }
}
