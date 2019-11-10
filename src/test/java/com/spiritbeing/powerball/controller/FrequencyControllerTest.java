package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.FrequencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

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
//        when(frequencyService.findPaginated(PageRequest.of(anyInt(),1)))
//                .thenReturn(new PageImpl<>(new ArrayList<>() , PageRequest.of(anyInt(),1, Sort.unsorted()), anyLong()));
//
//        mockMvc.perform(get("/showBallsFrequencies?page=1&size=1"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("ballFrequencies, pageNumbers"))
//                .andExpect(view().name("frequency/frequency"));
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
    void probability() {
    }
}