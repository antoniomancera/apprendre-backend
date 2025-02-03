package com.antonio.apprendrebackend.service.controller;

import com.antonio.apprendrebackend.service.model.Home;
import com.antonio.apprendrebackend.service.service.HomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeControllerTest.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeService homeService;

 
}
