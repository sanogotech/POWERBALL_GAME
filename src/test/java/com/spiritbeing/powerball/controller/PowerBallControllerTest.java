package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.model.PagerModel;
import com.spiritbeing.powerball.model.PowerBall;
import com.spiritbeing.powerball.service.NotificationService;
import com.spiritbeing.powerball.service.PowerBallService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PowerBallControllerTest {
    @Mock
    PowerBallService powerBallService;

    @Mock
    NotificationService notificationService;

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
        List<PowerBall> frequencies = new ArrayList<>();
        when(powerBallService.findByOrderByCreatedDateAsc(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(frequencies));

        mockMvc.perform(get("/showTable")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("pageSize","1")
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("table"))
                .andExpect(model().attributeExists("powerBallList","selectedPageSize", "pageSizes"));

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
    void save() throws Exception {
//        PowerBall powerBall = new PowerBall();
//         when(powerBallService.save(any())).thenReturn(powerBall);

         verifyNoInteractions(notificationService);

         mockMvc.perform(post("/save")
                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                 .param("powerBallUser", ""))
                 .andExpect(status().isOk())
                 .andExpect(view().name("addDraw"));

//        mockMvc.perform(post("/save")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("powerBallUser", "")
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/showTable"));

    }

    @Test
    void delete() throws Exception {
//        PowerBall powerBall = new PowerBall();
//        when(powerBallService.findById(anyLong())).thenReturn(powerBall);
//
////        verifyNoInteractions(notificationService);
////        verify(powerBallService.delete(any()), times(1));
//
//        mockMvc.perform(get("/delete")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("postId", "1L"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/showTable"));

    }
}