package com.proect.covidstats.service.impl;

import com.proect.covidstats.model.dto.CountryCovidStatsDto;
import com.proect.covidstats.model.dto.CovidStatsApiResponseDto;
import com.proect.covidstats.model.entity.CountryCovidStats;
import com.proect.covidstats.repository.CountryCovidStatsRepository;
import com.proect.covidstats.service.CovidStatsService;
import com.proect.covidstats.util.ApiConstants;
import com.proect.covidstats.util.converters.ObjectConverter;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.proect.covidstats.util.ExceptionConstants.*;

@Service
public class CovidStatsServiceImpl implements CovidStatsService {

    private final RestTemplate restTemplate;

    private final CountryCovidStatsRepository countryCovidStatsRepository;


    public CovidStatsServiceImpl(RestTemplate restTemplate, CountryCovidStatsRepository countryCovidStatsRepository) {
        this.restTemplate = restTemplate;
        this.countryCovidStatsRepository = countryCovidStatsRepository;
    }

    @Transactional
    @PostConstruct
    @Override
    public void initCovidStatsData() {
        ResponseEntity<CovidStatsApiResponseDto> response = restTemplate.getForEntity(ApiConstants.COVID_STATS_URI, CovidStatsApiResponseDto.class);
        CovidStatsApiResponseDto covidStatsApiResponseDto = response.getBody();
        if (covidStatsApiResponseDto == null) {
            throw new IllegalStateException(COULD_NOT_INITIALIZE_STATS);
        } else {
            List<CountryCovidStatsDto> countryCovidStatsDtoList = covidStatsApiResponseDto.getCountries();
            if (covidStatsApiResponseDto.getCountries() == null || covidStatsApiResponseDto.getCountries().isEmpty()) {
                throw new IllegalStateException( COULD_NOT_INITIALIZE_STATS);
            }

            List<CountryCovidStats> countryCovidStatsList = ObjectConverter.convertList(countryCovidStatsDtoList, CountryCovidStats.class);
            countryCovidStatsRepository.saveAll(countryCovidStatsList);
        }
    }

    @Override
    public CountryCovidStatsDto getCountryCovidStatsByCountryCode(String countryCode) {
        validateCountryCode(countryCode);

        CountryCovidStats countryCovidStats = countryCovidStatsRepository.findCountryCovidStatsByCountryCode(countryCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format(COULD_NOT_FIND_COUNTRY_CODE, countryCode)));

        return ObjectConverter.convertObject(countryCovidStats, CountryCovidStatsDto.class);
    }

    private void validateCountryCode(String countryCode) {
        String onlyUpperCaseRegex = "^[A-Z]+$";
        if (!countryCode.matches(onlyUpperCaseRegex)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    INVALID_COUNTRY_CODE);
        }
    }
}

