package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Category;
import com.antonio.apprendrebackend.service.model.WordSense;
import com.antonio.apprendrebackend.service.model.WordSenseCategory;
import com.antonio.apprendrebackend.service.repository.WordSenseCategoryRepository;
import com.antonio.apprendrebackend.service.service.impl.WordSenseCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordSenseCategoryServiceImplTest {
    @Mock
    private WordSenseCategoryRepository wordSenseCategoryRepository;

    @InjectMocks
    private WordSenseCategoryServiceImpl wordSenseCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCategoryByWordSenseIdSuccess() {
        // Given
        Integer wordSenseId = 1;

        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Grammar");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Vocabulary");

        Category category3 = new Category();
        category3.setId(3);
        category3.setName("Pronunciation");

        WordSense wordSense = new WordSense();
        wordSense.setId(wordSenseId);

        WordSenseCategory wsc1 = new WordSenseCategory();
        wsc1.setWordSense(wordSense);
        wsc1.setCategory(category1);

        WordSenseCategory wsc2 = new WordSenseCategory();
        wsc2.setWordSense(wordSense);
        wsc2.setCategory(category2);

        WordSenseCategory wsc3 = new WordSenseCategory();
        wsc3.setWordSense(wordSense);
        wsc3.setCategory(category3);

        List<WordSenseCategory> wordSenseCategories = Arrays.asList(wsc1, wsc2, wsc3);

        // When
        when(wordSenseCategoryRepository.findByWordSenseId(wordSenseId)).thenReturn(wordSenseCategories);
        List<Category> result = wordSenseCategoryService.getCategoryByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Grammar", result.get(0).getName());
        assertEquals("Vocabulary", result.get(1).getName());
        assertEquals("Pronunciation", result.get(2).getName());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());

        verify(wordSenseCategoryRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetCategoryByWordSenseIdEmpty() {
        // Given
        Integer wordSenseId = 999;
        List<WordSenseCategory> emptyWordSenseCategories = new ArrayList<>();

        // When
        when(wordSenseCategoryRepository.findByWordSenseId(wordSenseId)).thenReturn(emptyWordSenseCategories);
        List<Category> result = wordSenseCategoryService.getCategoryByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        verify(wordSenseCategoryRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetCategoryByWordSenseIdWithSingleCategory() {
        // Given
        Integer wordSenseId = 1;

        Category category = new Category();
        category.setId(1);
        category.setName("Single Category");

        WordSense wordSense = new WordSense();
        wordSense.setId(wordSenseId);

        WordSenseCategory wsc = new WordSenseCategory();
        wsc.setWordSense(wordSense);
        wsc.setCategory(category);

        List<WordSenseCategory> wordSenseCategories = Arrays.asList(wsc);

        // When
        when(wordSenseCategoryRepository.findByWordSenseId(wordSenseId)).thenReturn(wordSenseCategories);
        List<Category> result = wordSenseCategoryService.getCategoryByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Single Category", result.get(0).getName());
        assertEquals(1, result.get(0).getId());

        verify(wordSenseCategoryRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetCategoryByWordSenseIdWithNullParameter() {
        // Given
        Integer wordSenseId = null;
        List<WordSenseCategory> emptyWordSenseCategories = new ArrayList<>();

        // When
        when(wordSenseCategoryRepository.findByWordSenseId(wordSenseId)).thenReturn(emptyWordSenseCategories);
        List<Category> result = wordSenseCategoryService.getCategoryByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(wordSenseCategoryRepository, times(1)).findByWordSenseId(wordSenseId);
    }

    @Test
    void testGetCategoryByWordSenseIdWithDuplicateCategories() {
        // Given
        Integer wordSenseId = 1;

        Category category = new Category();
        category.setId(1);
        category.setName("Duplicate Category");

        WordSense wordSense = new WordSense();
        wordSense.setId(wordSenseId);

        WordSenseCategory wsc1 = new WordSenseCategory();
        wsc1.setWordSense(wordSense);
        wsc1.setCategory(category);

        WordSenseCategory wsc2 = new WordSenseCategory();
        wsc2.setWordSense(wordSense);
        wsc2.setCategory(category);

        List<WordSenseCategory> wordSenseCategories = Arrays.asList(wsc1, wsc2);

        // When
        when(wordSenseCategoryRepository.findByWordSenseId(wordSenseId)).thenReturn(wordSenseCategories);
        List<Category> result = wordSenseCategoryService.getCategoryByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size()); // El método no elimina duplicados
        assertEquals("Duplicate Category", result.get(0).getName());
        assertEquals("Duplicate Category", result.get(1).getName());

        verify(wordSenseCategoryRepository, times(1)).findByWordSenseId(wordSenseId);
    }


    @Test
    void testGetCategoryByWordSenseIdVerifyStreamProcessing() {
        // Given
        Integer wordSenseId = 1;

        Category category1 = new Category();
        category1.setId(10);
        category1.setName("Test Category 1");

        Category category2 = new Category();
        category2.setId(20);
        category2.setName("Test Category 2");

        WordSense wordSense = new WordSense();
        wordSense.setId(wordSenseId);

        WordSenseCategory wsc1 = new WordSenseCategory();
        wsc1.setCategory(category1);

        WordSenseCategory wsc2 = new WordSenseCategory();
        wsc2.setCategory(category2);

        List<WordSenseCategory> wordSenseCategories = Arrays.asList(wsc1, wsc2);

        // When
        when(wordSenseCategoryRepository.findByWordSenseId(wordSenseId)).thenReturn(wordSenseCategories);
        List<Category> result = wordSenseCategoryService.getCategoryByWordSenseId(wordSenseId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(cat -> cat.getId() == 10 && "Test Category 1".equals(cat.getName())));
        assertTrue(result.stream().anyMatch(cat -> cat.getId() == 20 && "Test Category 2".equals(cat.getName())));

        verify(wordSenseCategoryRepository, times(1)).findByWordSenseId(wordSenseId);
    }
}
