package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.UserInfo;

public class UserInfoImplTest {
    /*
    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @Mock
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserInfo_ReturnsUserInfo() {
        // Given
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setEmail("test@example.com");

        when(userInfoRepository.findFirstByOrderByDateAsc())
                .thenReturn(mockUserInfo);

        // When
        UserInfo result = userInfoService.getUserInfo();

        // Then
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("test@example.com", result.getEmail(), "El email debería coincidir");
        verify(userInfoRepository, times(1)).findFirstByOrderByDateAsc();
    }

    @Test
    void testGetByEmail_ReturnsUserInfo_WhenUserExists() {
        // Given
        String email = "test@example.com";
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setEmail(email);

        when(userInfoRepository.findByEmail(email))
                .thenReturn(Optional.of(mockUserInfo));

        // When
        UserInfo result = userInfoService.getByEmail(email);

        // Then
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals(email, result.getEmail(), "El email debería coincidir");
        verify(userInfoRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetByEmail_ThrowsException_WhenUserNotFound() {
        // Given
        String email = "nonexistent@example.com";

        when(userInfoRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        // When / Then
        UserInfoNotFoundException exception = assertThrows(
                UserInfoNotFoundException.class,
                () -> userInfoService.getByEmail(email)
        );

        assertEquals("Not found userInfo with email: " + email, exception.getMessage());
        verify(userInfoRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindBySupabaseId_ReturnsUserInfo_WhenUserExists() {
        // Given
        String supabaseId = "abc123";
        UserInfo mockUserInfo = new UserInfo();
        mockUserInfo.setSupabaseId(supabaseId);

        when(userInfoRepository.findBySupabaseId(supabaseId))
                .thenReturn(Optional.of(mockUserInfo));

        // When
        UserInfo result = userInfoService.findBySupabaseId(supabaseId);

        // Then
        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals(supabaseId, result.getSupabaseId(), "El supabaseId debería coincidir");
        verify(userInfoRepository, times(1)).findBySupabaseId(supabaseId);
    }

    @Test
    void testFindBySupabaseId_ThrowsException_WhenUserNotFound() {
        // Given
        String supabaseId = "nonexistent";

        when(userInfoRepository.findBySupabaseId(supabaseId))
                .thenReturn(Optional.empty());

        // When / Then
        UserInfoNotFoundException exception = assertThrows(
                UserInfoNotFoundException.class,
                () -> userInfoService.findBySupabaseId(supabaseId)
        );

        assertEquals("Not found userInfo", exception.getMessage());
        verify(userInfoRepository, times(1)).findBySupabaseId(supabaseId);
    }

     */
}
