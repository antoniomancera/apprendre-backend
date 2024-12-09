package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.UserRequestExistLastWeekNotAnswered;
import com.antonio.apprendrebackend.service.model.UserInfo;
import com.antonio.apprendrebackend.service.model.UserRequest;
import com.antonio.apprendrebackend.service.repository.UserRequestRepository;
import com.antonio.apprendrebackend.service.service.impl.UserRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserRequestServiceImplTest {
    @InjectMocks
    private UserRequestServiceImpl userRequestService;

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private UserRequestRepository userRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUserRequest_SuccessfulSave() {
        // Given
        String email = "test@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setEmail(email);

        when(userInfoService.getByEmail(email)).thenReturn(mockUserInfo);
        when(userRequestRepository.findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(any(), anyLong()))
                .thenReturn(Optional.empty());

        UserRequest mockSavedRequest = new UserRequest(subject, message, mockUserInfo);
        when(userRequestRepository.save(any(UserRequest.class))).thenReturn(mockSavedRequest);

        // When
        UserRequest result = userRequestService.addUserRequest(email, subject, message);

        // Then
        assertNotNull(result);
        assertEquals(subject, result.getSubject());
        assertEquals(message, result.getMessage());
        verify(userInfoService, times(1)).getByEmail(email);
        verify(userRequestRepository, times(1)).findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(any(), anyLong());
        verify(userRequestRepository, times(1)).save(any(UserRequest.class));
    }

    @Test
    void testAddUserRequest_ExistingRequestThrowsException() {
        // Given
        String email = "test@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setEmail(email);

        UserRequest existingRequest = new UserRequest();
        existingRequest.setAnswered(false);

        when(userInfoService.getByEmail(email)).thenReturn(mockUserInfo);
        when(userRequestRepository.findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(any(), anyLong()))
                .thenReturn(Optional.of(existingRequest));

        // When & Then
        UserRequestExistLastWeekNotAnswered exception = assertThrows(
                UserRequestExistLastWeekNotAnswered.class,
                () -> userRequestService.addUserRequest(email, subject, message)
        );

        assertEquals("There is already an unanswered request for this user from the last week.", exception.getMessage());
        verify(userInfoService, times(1)).getByEmail(email);
        verify(userRequestRepository, times(1)).findFirstByUserInfoAndIsAnsweredFalseAndCreatedDateAfter(any(), anyLong());
        verify(userRequestRepository, never()).save(any(UserRequest.class));
    }
}
