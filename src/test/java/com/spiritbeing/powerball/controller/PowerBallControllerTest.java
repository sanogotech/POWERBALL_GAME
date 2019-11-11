package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.model.PagerModel;
import com.spiritbeing.powerball.model.PowerBall;
import com.spiritbeing.powerball.service.PowerBallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PowerBallControllerTest {
    @Mock
    PowerBallService powerBallService;

    @InjectMocks
    PowerBallController controller;

    @InjectMocks
    PagerModel pagerModel;


    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        pagerModel = new PagerModel(1,1,1);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void homePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homePage"));
    }

    @Test
    void showTable() throws Exception {


    }

    @Test
    void drawBalls() throws Exception {
        mockMvc.perform(get("/drawBalls"))
                .andExpect(flash().attributeExists("drawnBalls"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void inputDraw() throws Exception {
        mockMvc.perform(get("/inputDraw"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("powerBallUser"))
                .andExpect(view().name("addDraw"));
    }

    @Test
    void showFormForUpdate() throws Exception {

        when(powerBallService.findById(anyLong())).thenReturn(new PowerBall());

        mockMvc.perform(get("/showFormForUpdate?postId=1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("powerBallUser"))
                .andExpect(view().name("addDraw"));
    }

    @Test
    void save() {

    }

    @Test
    void delete() throws Exception {
//
    }
}