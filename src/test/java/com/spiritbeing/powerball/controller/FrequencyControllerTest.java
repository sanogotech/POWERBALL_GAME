package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.FrequencyService;
import com.spiritbeing.powerball.model.BallsFrequency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FrequencyControllerTest {

    @Mock
    FrequencyService frequencyService;

   @InjectMocks
   FrequencyController controller;

   MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showBallsFrequencies() throws Exception {
        List<BallsFrequency> frequencies = new ArrayList<>();
        when(frequencyService.findPaginated(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(frequencies));

        mockMvc.perform(get("/showBallsFrequencies?page=1&size=1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page","0")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ballFrequencies"))
                .andExpect(view().name("frequency/frequency"));
    }

    @Test
    void top10() throws Exception {

        when(frequencyService.top10()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/top10"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("top10"))
                .andExpect(view().name("frequency/top10"));
    }

    @Test
    void probability() throws Exception {
        List<BallsFrequency> frequencies = new ArrayList<>();
        when(frequencyService.findPaginated(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(frequencies));

        mockMvc.perform(get("/probability")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page","1")
                .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("frequency/probability"))
                .andExpect(model().attributeExists("ballFrequencies","totalRedValue", "totalWhiteValue"));
    }
}