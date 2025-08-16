package com.antonio.apprendrebackend.service.service;


import com.antonio.apprendrebackend.service.exception.TypeNotFoundException;
import com.antonio.apprendrebackend.service.model.Type;
import com.antonio.apprendrebackend.service.repository.TypeRepository;
import com.antonio.apprendrebackend.service.service.impl.TypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TypeServiceImplTest {
    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private TypeServiceImpl typeService;

    private Type type;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        type = new Type();
        type.setId(1);
        type.setCode(Type.TypeEnum.VERB);
    }

    @Test
    void testGetByTypeSuccess() {
        // Given
        Type.TypeEnum typeEnum = Type.TypeEnum.VERB;
        Optional<Type> typeOptional = Optional.of(type);

        // When
        when(typeRepository.findByCode(typeEnum)).thenReturn(typeOptional);
        Type result = typeService.getByType(typeEnum);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(Type.TypeEnum.VERB, result.getCode());
        verify(typeRepository, times(1)).findByCode(typeEnum);
    }

    @Test
    void testGetByTypeNotFound() {
        // Given
        Type.TypeEnum typeEnum = Type.TypeEnum.SUSTANTIVE;
        Optional<Type> emptyOptional = Optional.empty();

        // When
        when(typeRepository.findByCode(typeEnum)).thenReturn(emptyOptional);

        // Then
        TypeNotFoundException exception = assertThrows(TypeNotFoundException.class, () -> {
            typeService.getByType(typeEnum);
        });

        assertTrue(exception.getMessage().contains("Not found any type"));
        assertTrue(exception.getMessage().contains(typeEnum.toString()));
        verify(typeRepository, times(1)).findByCode(typeEnum);
    }

    @Test
    void testGetByTypeWithDifferentEnumValues() {
        // Given
        Type.TypeEnum typeEnum = Type.TypeEnum.SUSTANTIVE;
        Type phraseType = new Type();
        phraseType.setId(2);
        phraseType.setCode(Type.TypeEnum.SUSTANTIVE);
        Optional<Type> typeOptional = Optional.of(phraseType);

        // When
        when(typeRepository.findByCode(typeEnum)).thenReturn(typeOptional);

        Type result = typeService.getByType(typeEnum);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(Type.TypeEnum.SUSTANTIVE, result.getCode());
        verify(typeRepository, times(1)).findByCode(typeEnum);
    }

    @Test
    void testGetAllTypesSuccess() {
        // Given
        Type type1 = new Type();
        type1.setId(1);
        type1.setCode(Type.TypeEnum.SUSTANTIVE);

        Type type2 = new Type();
        type2.setId(2);
        type2.setCode(Type.TypeEnum.VERB);

        Type type3 = new Type();
        type3.setId(3);
        type3.setCode(Type.TypeEnum.ADJECTIVE);

        List<Type> types = Arrays.asList(type1, type2, type3);

        // When
        when(typeRepository.findAll()).thenReturn(types);
        List<Type> result = typeService.getAllTypes();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(Type.TypeEnum.SUSTANTIVE, result.get(0).getCode());
        assertEquals(Type.TypeEnum.VERB, result.get(1).getCode());
        assertEquals(Type.TypeEnum.ADJECTIVE, result.get(2).getCode());
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTypesEmpty() {
        // Given
        List<Type> emptyTypes = Collections.emptyList();

        // When
        when(typeRepository.findAll()).thenReturn(emptyTypes);
        List<Type> result = typeService.getAllTypes();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTypesNull() {
        // Given / When
        when(typeRepository.findAll()).thenReturn(null);
        List<Type> result = typeService.getAllTypes();

        // Then
        assertNull(result);
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTypesSingleType() {
        // Given
        Type singleType = new Type();
        singleType.setId(1);
        singleType.setCode(Type.TypeEnum.ADVERB);

        List<Type> types = Arrays.asList(singleType);

        // When
        when(typeRepository.findAll()).thenReturn(types);
        List<Type> result = typeService.getAllTypes();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(Type.TypeEnum.ADVERB, result.get(0).getCode());
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTypesRepositoryCalledOnce() {
        // Given
        List<Type> types = Arrays.asList(new Type(), new Type());

        // When
        when(typeRepository.findAll()).thenReturn(types);
        typeService.getAllTypes();

        // Then
        verify(typeRepository, times(1)).findAll();
        verifyNoMoreInteractions(typeRepository);
    }
}
