package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.HighChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class ChartController {

    private final HighChartService highChartService;

    public ChartController(HighChartService highChartService) {
        this.highChartService = highChartService;
    }

    @GetMapping("/showBarChart")
    public String showChart(Model model){
        Map<Integer, Long> coordinates = highChartService.getChartCoordinates();
        model.addAttribute("coordinates", coordinates);
        return "chart/barChart";
    }

    @GetMapping("/showPieChart")
    public String showPieChart(Model model){
        Map<Integer, Long> coordinates = highChartService.getChartCoordinates();
        model.addAttribute("coordinates", coordinates);
        return "chart/pieChart";
    }
}
