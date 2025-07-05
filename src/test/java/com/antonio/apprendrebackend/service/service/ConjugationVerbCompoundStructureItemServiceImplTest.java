package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructure;
import com.antonio.apprendrebackend.service.model.ConjugationVerbCompoundStructureItem;
import com.antonio.apprendrebackend.service.repository.ConjugationVerbCompoundStructureItemRepository;
import com.antonio.apprendrebackend.service.service.impl.ConjugationVerbCompoundStructureItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ConjugationVerbCompoundStructureItemServiceImplTest {
    @Mock
    private ConjugationVerbCompoundStructureItemRepository conjugationVerbCompoundStructureItemRepository;

    @Mock
    private ConjugationVerbCompoundStructureService conjugationVerbCompoundStructureService;

    @InjectMocks
    private ConjugationVerbCompoundStructureItemServiceImpl conjugationVerbCompoundStructureItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests for getConjugationVerbCompoundStructureItemsByTenseId method

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WhenCompoundStructureExists() {
        // Given
        Integer tenseId = 1;
        Integer compoundStructureId = 10;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(compoundStructureId);

        ConjugationVerbCompoundStructureItem item1 = new ConjugationVerbCompoundStructureItem();
        item1.setId(1);
        item1.setConjugationVerbCompoundStructure(compoundStructure);

        ConjugationVerbCompoundStructureItem item2 = new ConjugationVerbCompoundStructureItem();
        item2.setId(2);
        item2.setConjugationVerbCompoundStructure(compoundStructure);

        List<ConjugationVerbCompoundStructureItem> expectedItems = Arrays.asList(item1, item2);

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(compoundStructure);
        when(conjugationVerbCompoundStructureItemRepository.findByConjugationVerbCompoundStructureId(compoundStructureId))
                .thenReturn(expectedItems);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(compoundStructureId, result.get(0).getConjugationVerbCompoundStructure().getId());
        assertEquals(compoundStructureId, result.get(1).getConjugationVerbCompoundStructure().getId());

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, times(1))
                .findByConjugationVerbCompoundStructureId(compoundStructureId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WhenCompoundStructureExistsButNoItems() {
        // Given
        Integer tenseId = 1;
        Integer compoundStructureId = 10;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(compoundStructureId);

        List<ConjugationVerbCompoundStructureItem> emptyItems = Collections.emptyList();

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(compoundStructure);
        when(conjugationVerbCompoundStructureItemRepository.findByConjugationVerbCompoundStructureId(compoundStructureId))
                .thenReturn(emptyItems);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, times(1))
                .findByConjugationVerbCompoundStructureId(compoundStructureId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WhenCompoundStructureDoesNotExist() {
        // Given
        Integer tenseId = 999;

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(null);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNull(result);

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, never())
                .findByConjugationVerbCompoundStructureId(anyInt());
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WhenCompoundStructureExistsWithSingleItem() {
        // Given
        Integer tenseId = 2;
        Integer compoundStructureId = 20;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(compoundStructureId);

        ConjugationVerbCompoundStructureItem singleItem = new ConjugationVerbCompoundStructureItem();
        singleItem.setId(5);
        singleItem.setConjugationVerbCompoundStructure(compoundStructure);

        List<ConjugationVerbCompoundStructureItem> singleItemList = Arrays.asList(singleItem);

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(compoundStructure);
        when(conjugationVerbCompoundStructureItemRepository.findByConjugationVerbCompoundStructureId(compoundStructureId))
                .thenReturn(singleItemList);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getId());
        assertEquals(compoundStructureId, result.get(0).getConjugationVerbCompoundStructure().getId());

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, times(1))
                .findByConjugationVerbCompoundStructureId(compoundStructureId);
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WhenServiceThrowsException() {
        // Given
        Integer tenseId = 1;

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenThrow(new RuntimeException("Database connection error"));

        // When // Then
        assertThrows(RuntimeException.class, () -> {
            conjugationVerbCompoundStructureItemService
                    .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);
        });

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, never())
                .findByConjugationVerbCompoundStructureId(anyInt());
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WhenRepositoryThrowsException() {
        // Given
        Integer tenseId = 1;
        Integer compoundStructureId = 10;

        ConjugationVerbCompoundStructure compoundStructure = new ConjugationVerbCompoundStructure();
        compoundStructure.setId(compoundStructureId);

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(compoundStructure);
        when(conjugationVerbCompoundStructureItemRepository.findByConjugationVerbCompoundStructureId(compoundStructureId))
                .thenThrow(new RuntimeException("Repository error"));

        // When // Then
        assertThrows(RuntimeException.class, () -> {
            conjugationVerbCompoundStructureItemService
                    .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);
        });

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, times(1))
                .findByConjugationVerbCompoundStructureId(compoundStructureId);
    }

    // Edge cases

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WithNullTenseId() {
        // Given
        Integer tenseId = null;

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(null);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNull(result);

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, never())
                .findByConjugationVerbCompoundStructureId(anyInt());
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WithZeroTenseId() {
        // Given
        Integer tenseId = 0;

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(null);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNull(result);

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, never())
                .findByConjugationVerbCompoundStructureId(anyInt());
    }

    @Test
    void testGetConjugationVerbCompoundStructureItemsByTenseId_WithNegativeTenseId() {
        // Given
        Integer tenseId = -1;

        when(conjugationVerbCompoundStructureService.getConjugationVerbCompoundStructureByTenseId(tenseId))
                .thenReturn(null);

        // When
        List<ConjugationVerbCompoundStructureItem> result = conjugationVerbCompoundStructureItemService
                .getConjugationVerbCompoundStructureItemsByTenseId(tenseId);

        // Then
        assertNull(result);

        verify(conjugationVerbCompoundStructureService, times(1))
                .getConjugationVerbCompoundStructureByTenseId(tenseId);
        verify(conjugationVerbCompoundStructureItemRepository, never())
                .findByConjugationVerbCompoundStructureId(anyInt());
    }
}
