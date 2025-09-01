package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.model.Category;
import com.antonio.apprendrebackend.service.repository.CategoryRepository;
import com.antonio.apprendrebackend.service.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategoriesSuccess() {
        // Given
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Technology");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Science");

        Category category3 = new Category();
        category3.setId(3);
        category3.setName("History");

        List<Category> categories = Arrays.asList(category1, category2, category3);

        // When
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Technology", result.get(0).getName());
        assertEquals("Science", result.get(1).getName());
        assertEquals("History", result.get(2).getName());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCategoriesEmpty() {
        // Given
        List<Category> emptyCategories = Collections.emptyList();

        // When
        when(categoryRepository.findAll()).thenReturn(emptyCategories);
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCategoriesNull() {
        // Given / When
        when(categoryRepository.findAll()).thenReturn(null);
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertNull(result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCategoriesSingleCategory() {
        // Given
        Category singleCategory = new Category();
        singleCategory.setId(1);
        singleCategory.setName("Single Category");

        List<Category> categories = Arrays.asList(singleCategory);

        // When
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Single Category", result.get(0).getName());
        assertEquals(1, result.get(0).getId());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCategoriesRepositoryCalledOnce() {
        // Given
        List<Category> categories = Arrays.asList(new Category(), new Category());

        // When
        when(categoryRepository.findAll()).thenReturn(categories);
        categoryService.getAllCategories();

        // Then
        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }
}
