package com.chandel.coronavirustracker.controllers;

import com.chandel.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CovidDataController {

    // Injecting the service instance into the controller to use the service data

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    // Creating a router mapping and passing data from service to the mapper

    @GetMapping("/")
    public String getData(Model model){
        int currentDateCases = coronaVirusDataService.getCurrentDayStats().stream().mapToInt(stat -> Integer.parseInt(stat.getCasesForToday())).sum();
        int previousDateCases = coronaVirusDataService.getPreviousDayStats().stream().mapToInt(stat -> Integer.parseInt(stat.getCasesForToday())).sum();
        int dailyChange = currentDateCases-previousDateCases;
        model.addAttribute("currentDataCases", currentDateCases);
        model.addAttribute("previousDateCases", previousDateCases);
        model.addAttribute("dailyChange", dailyChange);
        model.addAttribute("locationStatsList", coronaVirusDataService.getCurrentDayStats());
        return "home";
    }
}
