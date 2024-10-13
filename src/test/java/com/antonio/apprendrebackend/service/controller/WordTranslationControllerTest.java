package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.dto.PhraseDTO;
import com.antonio.apprendrebackend.service.dto.WordTranslationDTO;
import com.antonio.apprendrebackend.service.model.WordFr;
import com.antonio.apprendrebackend.service.model.WordSp;
import com.antonio.apprendrebackend.service.service.WordTranslationService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@WebMvcTest(WordTranslationControllerTest.class)
public class WordTranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordTranslationService wordTranslationService;


    @Test
    public void testGetRandomWordTranslationPhrase() throws Exception {

        WordTranslationDTO wordTranslationDTO = new WordTranslationDTO();

        given(wordTranslationService.getRandomWordTranslation()).willReturn(wordTranslationDTO);

        mockMvc.perform(get("/wordTranslation/getRandom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRandomWordTranslationPhrase_NotFound() throws Exception {

        given(wordTranslationService.getRandomWordTranslation()).willReturn(null);

        mockMvc.perform(get("/wordTranslation/getRandom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
