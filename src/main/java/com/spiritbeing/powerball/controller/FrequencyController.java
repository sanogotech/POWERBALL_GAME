package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.FrequencyService;
import com.spiritbeing.powerball.abstractModel.Constants;
import com.spiritbeing.powerball.model.BallsFrequency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
public class FrequencyController extends Constants {

    private final FrequencyService frequencyService;

    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

//    @GetMapping("/showBallsFrequencies")
//    public String showBallsFrequencies(@RequestParam("pageSize") Optional<Integer> pageSize,
//                                       @RequestParam("page") Optional<Integer> page, Model model){
//
//        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
//
//        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
//
//        Page<BallsFrequency> ballsFrequencies = frequencyService.findByOrderById(PageRequest.of(evalPage, evalPageSize));
//
//        PagerModel pager = new PagerModel(ballsFrequencies.getTotalPages(),ballsFrequencies.getNumber(),BUTTONS_TO_SHOW);
//
//        model.addAttribute("ballsFrequencies",ballsFrequencies);
//        model.addAttribute("selectedPageSize", evalPageSize);
//        model.addAttribute("pageSizes", PAGE_SIZES);
//        model.addAttribute("pager", pager);
//
//        return "frequencyTable";
//    }

    @GetMapping("/showBallsFrequencies")
    public String showBallsFrequencies(@RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<BallsFrequency> ballFrequencies = frequencyService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("ballFrequencies", ballFrequencies);

        int totalPages = ballFrequencies.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "frequency/frequency";
    }

    @GetMapping("/top10")
    public String top10(Model model) {
        List<BallsFrequency> top10 = frequencyService.top10();
        model.addAttribute("top10", top10);
        return "frequency/top10";
    }

    @GetMapping("/probability")
    public String probability(@RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<BallsFrequency> ballFrequencies = frequencyService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("ballFrequencies", ballFrequencies);
        model.addAttribute("totalRedValue", frequencyService.redTotalValue());
        model.addAttribute("totalWhiteValue", frequencyService.whiteTotalValue());

        int totalPages = ballFrequencies.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "frequency/probability";
    }

}
