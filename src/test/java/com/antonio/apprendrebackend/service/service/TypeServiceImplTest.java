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

}
