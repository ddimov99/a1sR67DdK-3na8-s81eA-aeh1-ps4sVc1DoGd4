package com.proect.covidstats.service;

import com.proect.covidstats.model.dto.CountryCovidStatsDto;

public interface CovidStatsService {
    void initCovidStatsData();

    CountryCovidStatsDto getCountryCovidStatsByCountryCode(String countryCode);
}
