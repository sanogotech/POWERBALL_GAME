package com.spiritbeing.powerball.controller;

import com.spiritbeing.powerball.service.PowerBallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PowerBallRestController {

    private final PowerBallService service;

    @GetMapping("/powerBallRest/Native")
    @ResponseBody//optional, since RestController already contains @ResponseBody
    public Double powerBallRest(){
        return service.sumOfEntryNative();
    }

    @GetMapping("/powerBallRest/JPQL")
    @ResponseBody//optional, since RestController already contains @ResponseBody
    public Double powerBallRestJPQL(){
        return service.sumOfEntryJPQL();
    }
}
