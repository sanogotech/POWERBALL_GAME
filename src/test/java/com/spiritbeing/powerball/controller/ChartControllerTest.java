package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.HighChartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ChartControllerTest {

    @Mock
    HighChartService highChartService;

    @InjectMocks
    ChartController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showChart() throws Exception{
        when(highChartService.getChartCoordinates()).thenReturn(new LinkedHashMap<>());

        mockMvc.perform(get("/showBarChart"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("coordinates"))
                .andExpect(view().name("chart/barChart"));
    }

    @Test
    void showPieChart() throws Exception {
        when(highChartService.getChartCoordinates()).thenReturn(new LinkedHashMap<>());

        mockMvc.perform(get("/showPieChart"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("coordinates"))
                .andExpect(view().name("chart/pieChart"));
    }
}