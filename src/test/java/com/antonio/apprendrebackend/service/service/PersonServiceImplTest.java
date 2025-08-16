package com.antonio.apprendrebackend.service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antonio.apprendrebackend.service.model.Person;
import com.antonio.apprendrebackend.service.repository.PersonRepository;
import com.antonio.apprendrebackend.service.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPersonsSuccess() {
        // Given
        Person person1 = new Person();
        person1.setId(1);
        person1.setCode("First Person");

        Person person2 = new Person();
        person2.setId(2);
        person2.setCode("Second Person");

        Person person3 = new Person();
        person3.setId(3);
        person3.setCode("Third Person");

        List<Person> persons = Arrays.asList(person1, person2, person3);

        // When
        when(personRepository.findAll()).thenReturn(persons);
        List<Person> result = personService.getAllPersons();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("First Person", result.get(0).getCode());
        assertEquals("Second Person", result.get(1).getCode());
        assertEquals("Third Person", result.get(2).getCode());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPersonsEmpty() {
        // Given
        List<Person> emptyPersons = Collections.emptyList();

        // When
        when(personRepository.findAll()).thenReturn(emptyPersons);
        List<Person> result = personService.getAllPersons();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPersonsNull() {
        // Given / When
        when(personRepository.findAll()).thenReturn(null);
        List<Person> result = personService.getAllPersons();

        // Then
        assertNull(result);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPersonsSinglePerson() {
        // Given
        Person singlePerson = new Person();
        singlePerson.setId(1);
        singlePerson.setCode("Only Person");

        List<Person> persons = Arrays.asList(singlePerson);

        // When
        when(personRepository.findAll()).thenReturn(persons);
        List<Person> result = personService.getAllPersons();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Only Person", result.get(0).getCode());
        assertEquals(1, result.get(0).getId());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPersonsRepositoryCalledOnce() {
        // Given
        List<Person> persons = Arrays.asList(new Person(), new Person());

        // When
        when(personRepository.findAll()).thenReturn(persons);
        personService.getAllPersons();

        // Then
        verify(personRepository, times(1)).findAll();
        verifyNoMoreInteractions(personRepository);
    }
}