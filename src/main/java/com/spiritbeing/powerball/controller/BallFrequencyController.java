package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.FrequencyService;
import com.spiritbeing.powerball.abstractModel.Constants;
import com.spiritbeing.powerball.model.BallsFrequency;
import com.spiritbeing.powerball.model.PagerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BallFrequencyController extends Constants {

    private final FrequencyService frequencyService;

    public BallFrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @GetMapping("/showBallsFrequencies")
    public String showBallsFrequencies(@RequestParam("pageSize") Optional<Integer> pageSize,
                                       @RequestParam("page") Optional<Integer> page, Model model){

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);

        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<BallsFrequency> ballsFrequencies = frequencyService.findByOrderById(PageRequest.of(evalPage, evalPageSize));

        PagerModel pager = new PagerModel(ballsFrequencies.getTotalPages(),ballsFrequencies.getNumber(),BUTTONS_TO_SHOW);

        model.addAttribute("ballsFrequencies",ballsFrequencies);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("pager", pager);

        return "frequencyTable";
    }
}
