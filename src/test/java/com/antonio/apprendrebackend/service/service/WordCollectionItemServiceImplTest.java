package com.antonio.apprendrebackend.service.service;

import com.antonio.apprendrebackend.service.exception.WordCollectionItemNotFoundException;
import com.antonio.apprendrebackend.service.model.WordCollection;
import com.antonio.apprendrebackend.service.model.WordCollectionItem;
import com.antonio.apprendrebackend.service.repository.WordCollectionItemRepository;
import com.antonio.apprendrebackend.service.service.impl.WordCollectionItemServiceImpl;
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

public class WordCollectionItemServiceImplTest {
    @Mock
    private WordCollectionItemRepository wordCollectionItemRepository;

    @InjectMocks
    private WordCollectionItemServiceImpl wordCollectionItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWordCollectionItemsByEnumSuccess() {
        //Given
        WordCollection.WordCollectionEnum enumValue = WordCollection.WordCollectionEnum.CONJ_PRO_NON_IMP_FR;

        WordCollectionItem item1 = new WordCollectionItem();
        WordCollectionItem item2 = new WordCollectionItem();
        List<WordCollectionItem> items = Arrays.asList(item1, item2);

        //When
        when(wordCollectionItemRepository.findByWordCollection_WordCollectionEnum(enumValue)).thenReturn(items);

        List<WordCollectionItem> result = wordCollectionItemService.getWordCollectionItemsByWordCollectionEnum(enumValue);

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(wordCollectionItemRepository, times(1)).findByWordCollection_WordCollectionEnum(enumValue);
    }

    @Test
    void testGetWordCollectionItemsByEnumEmpty() {
        //Given
        WordCollection.WordCollectionEnum enumValue = WordCollection.WordCollectionEnum.CONJ_PRO_NON_IMP_FR;

        //When
        when(wordCollectionItemRepository.findByWordCollection_WordCollectionEnum(enumValue)).thenReturn(Collections.emptyList());

        //Then
        WordCollectionItemNotFoundException exception = assertThrows(WordCollectionItemNotFoundException.class, () -> {
            wordCollectionItemService.getWordCollectionItemsByWordCollectionEnum(enumValue);
        });

        assertEquals("Not found any wordCollectionItem for wordCollection CONJ_PRO_NON_IMP_FR", exception.getMessage());
        verify(wordCollectionItemRepository, times(1)).findByWordCollection_WordCollectionEnum(enumValue);
    }
}
