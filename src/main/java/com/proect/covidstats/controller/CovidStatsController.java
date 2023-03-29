package com.proect.covidstats.controller;

import com.proect.covidstats.model.dto.CountryCovidStatsDto;
import com.proect.covidstats.service.CovidStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/covid-stats")
@RestController
public class CovidStatsController {

    private final CovidStatsService covidStatsService;


    public CovidStatsController(CovidStatsService covidStatsService) {
        this.covidStatsService = covidStatsService;
    }


    @GetMapping(path = "/country/{countryCode}")
    public ResponseEntity<CountryCovidStatsDto> getCountryStatsByCountryCode(@PathVariable String countryCode) {

        return ResponseEntity.status(HttpStatus.OK).body(covidStatsService.getCountryCovidStatsByCountryCode(countryCode));
    }


}
