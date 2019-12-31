package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.ServiceAddon.HighChartService;
import com.spiritbeing.powerball.abstractModel.Constants;
import com.spiritbeing.powerball.model.PagerModel;
import com.spiritbeing.powerball.model.PowerBall;
import com.spiritbeing.powerball.model.PowerBallDraw;
import com.spiritbeing.powerball.service.NotificationService;
import com.spiritbeing.powerball.service.PowerBallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller @Slf4j
public class PowerBallController extends Constants{

    private final NotificationService notificationService;
    private final PowerBallService powerBallService;
    private final HighChartService highChartService;

    public PowerBallController(NotificationService notificationService, PowerBallService powerBallService, HighChartService highChartService) {
        this.notificationService = notificationService;
        this.powerBallService = powerBallService;
        this.highChartService = highChartService;
    }

    @GetMapping("/")
    public String HomePage(){
        return "homePage";
    }

    @GetMapping("/showTable")
    public String showTable(@RequestParam("pageSize") Optional<Integer> pageSize,
                           @RequestParam("page") Optional<Integer> page,Model model){

        //
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        // print repo
        Page<PowerBall> powerBallList = powerBallService.findByOrderByCreatedDateAsc(PageRequest.of(evalPage, evalPageSize));
//        System.out.println("client list get total pages" + powerBallList.getTotalPages() + "client list get number " + powerBallList.getNumber());
        PagerModel pager = new PagerModel(powerBallList.getTotalPages(),powerBallList.getNumber(),BUTTONS_TO_SHOW);
        // add clientmodel
        model.addAttribute("powerBallList",powerBallList);
        // evaluate page size
        model.addAttribute("selectedPageSize", evalPageSize);
        // add page sizes
        model.addAttribute("pageSizes", PAGE_SIZES);
        // add pager
        model.addAttribute("pager", pager);
        return "table";
    }

    @GetMapping("/drawBalls")
    public String drawBalls(RedirectAttributes attributes){
        powerBallService.redBall().forEach((key, value) -> log.info("key: " + key + " value: " + value));
        attributes.addFlashAttribute("drawnBalls", powerBallService.drawnBalls()); //used for redirection to carry over attributes
        return "redirect:/";
    }

    @GetMapping("/inputDraw")
    public String inputDraw(Model model){
        log.debug("am in input draw method");

        model.addAttribute("powerBallUser", new PowerBallDraw());
        return "addDraw";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("postId") Long theId, Model theModel){
        PowerBall powerBall = powerBallService.findById(theId);

        //No need to convert since they match i.e entity powerBall n prototype powerBallDraw
        theModel.addAttribute("powerBallUser", powerBall);
        return "addDraw";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("powerBallUser") PowerBallDraw powerBallDraw,
                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            notificationService.addErrorMessage("add draw correctly");
            return "addDraw";
        }
        powerBallService.savePowerBallDraw(powerBallDraw);

        notificationService.addInfoMessage("Draw saved/Updated successfully");
        return "redirect:/showTable";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("postId") Long theId){
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        PowerBall powerBall = powerBallService.findById(theId);

        powerBallService.delete(powerBall);
        notificationService.addInfoMessage("PowerBall Draw with id " + theId + " was deleted by "
                + loggedInUsername  + " on " + powerBallService.getCurrentDate());
        return "redirect:/showTable";
    }
}
