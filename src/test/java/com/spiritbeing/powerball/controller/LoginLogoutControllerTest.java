package com.spiritbeing.powerball.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginLogoutControllerTest {

    @InjectMocks
    LoginLogoutController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/showMyLoginPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void logoutPage() {
    }
}