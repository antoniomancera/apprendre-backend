package com.antonio.apprendrebackend.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StatsServiceImplTest {
    @Mock
    DeckWordPhraseTranslationService deckWordPhraseTranslationService;

    @Mock
    StatsService statsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    @Test
    void testGetDailyStatsLastWeek_ReturnsDailyStats() {
        // Given
        LocalDate today = LocalDate.now();
        long endMillis = System.currentTimeMillis();
        long startMillis = today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        List<UserHistorial> mockHistorial = new ArrayList<>();
        UserHistorial wordTranslationHistorial1 = new UserHistorial();
        wordTranslationHistorial1.setDate(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        mockHistorial.add(wordTranslationHistorial1);

        UserHistorial wordTranslationHistorial2 = new UserHistorial();
        wordTranslationHistorial2.setDate(today.minusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        mockHistorial.add(wordTranslationHistorial2);

        when(deckUserWordPhraseTranslationService.getWordTranslationHistorialLastWeek()).thenReturn(Optional.of(mockHistorial));

        // When
        List<DailyStats> result = statsService.getDailyStatsLastWeek();

        // Then
        assertNotNull(result);
        assertEquals(7, result.size());
        assertEquals(3, result.get(0).getTotalSuccesses());
        assertEquals(5, result.get(1).getTotalSuccesses());
        verify(deckUserWordPhraseTranslationService, times(1)).getWordTranslationHistorialLastWeek();
    }

    @Test
    void testGetDailyStatsLastWeek_ThrowsWordTranslationHistorialNotFound() {
        // Given
        when(deckUserWordPhraseTranslationService.getWordTranslationHistorialLastWeek()).thenReturn(Optional.empty());

        // When / Then
        WordTranslationHistorialNotFound exception = assertThrows(WordTranslationHistorialNotFound.class,
                () -> statsService.getDailyStatsLastWeek());

        assertEquals("Not found any Word Translation Historial in last week", exception.getMessage());
        verify(deckUserWordPhraseTranslationService, times(1)).getWordTranslationHistorialLastWeek();
    }

     */


}
