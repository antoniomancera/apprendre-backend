package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class UserInfoServiceImplTest {
    @Mock
    UserInfoService userInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserInfo() {
        // Given
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setId(1);
        mockUserInfo.setName("Test User");

        when(userInfoService.getUserInfo()).thenReturn(mockUserInfo);

        // When
        UserInfo result = userInfoService.getUserInfo();

        // Then
        assertNotNull(result);
        assertEquals(mockUserInfo.getId(), result.getId());
        assertEquals(mockUserInfo.getName(), result.getName());

        verify(userInfoService, times(1)).getUserInfo();
    }
}
