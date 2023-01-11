package com.chandel.coronavirustracker.controllers;

import com.chandel.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class CovidDataController {

    // Injecting the service instance into the controller to use the service data

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    // Creating a router mapping and passing data from service to the mapper

    @GetMapping("/")
    public String getData(Model model){
        Date currentDate = new Date();
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("locationStatsList", coronaVirusDataService.getAllStats());
        return "home";
    }
}
