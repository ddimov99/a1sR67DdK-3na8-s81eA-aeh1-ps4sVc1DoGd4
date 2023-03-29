package com.proect.covidstats.service;

import com.proect.covidstats.model.dto.CountryCovidStatsDto;
import com.proect.covidstats.model.dto.CovidStatsApiResponseDto;
import com.proect.covidstats.model.entity.CountryCovidStats;
import com.proect.covidstats.repository.CountryCovidStatsRepository;
import com.proect.covidstats.service.impl.CovidStatsServiceImpl;
import com.proect.covidstats.util.ApiConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.proect.covidstats.util.ExceptionConstants.COULD_NOT_FIND_COUNTRY_CODE;
import static com.proect.covidstats.util.ExceptionConstants.INVALID_COUNTRY_CODE;

@ExtendWith(MockitoExtension.class)
public class CovidStatsServiceTest {

    @InjectMocks
    private CovidStatsServiceImpl covidStatsService;

    @Mock
    private CountryCovidStatsRepository countryCovidStatsRepository;

    @Mock
    private RestTemplate restTemplate;


    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_thenVerifyCountryCovidStatusesSuccessfullySaved() {
        List<CountryCovidStatsDto> countryCovidStatsDtoList = List.of(new CountryCovidStatsDto());
        CovidStatsApiResponseDto covidStatsApiResponseDto = new CovidStatsApiResponseDto();
        covidStatsApiResponseDto.setCountries(countryCovidStatsDtoList);

        Mockito.when(restTemplate.getForEntity(
                        ApiConstants.COVID_STATS_URI, CovidStatsApiResponseDto.class))
                .thenReturn(new ResponseEntity<>(covidStatsApiResponseDto, HttpStatus.OK));

        covidStatsService.initCovidStatsData();

        //Assert
        Mockito.verify(countryCovidStatsRepository, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    public void givenInvalidCountryCode_whenGetCountryCovidStatsByCountryCode_thenVerifyExceptionMessage() {
        Exception exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> covidStatsService.getCountryCovidStatsByCountryCode("Invalid code"));

        String expectedMessage = "400 BAD_REQUEST \"" + INVALID_COUNTRY_CODE + "\"";
        String actualMessage = exception.getMessage();

        //Assert
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenValidCountryCode_whenGetCountryCovidStatsByCountryCodeAndEntityFound_thenVerifyReturnedObject() {
        String dummyCode = "DM";
        CountryCovidStats countryCovidStats = createCountryCovidStats();

        Mockito.when(countryCovidStatsRepository.findCountryCovidStatsByCountryCode(dummyCode)).thenReturn(Optional.of(countryCovidStats));

        CountryCovidStatsDto expected = new CountryCovidStatsDto();
        expected.setCountry(countryCovidStats.getCountry());
        expected.setCountryCode(countryCovidStats.getCountryCode());

        CountryCovidStatsDto actual = covidStatsService.getCountryCovidStatsByCountryCode(dummyCode);


        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void givenValidCountryCode_whenGetCountryCovidStatsByCountryCodeAndEntityNotFound_thenVerifyReturnedObject() {
        String dummyCode = "DM";
        Mockito.when(countryCovidStatsRepository.findCountryCovidStatsByCountryCode(dummyCode)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> covidStatsService.getCountryCovidStatsByCountryCode(dummyCode));

        String expectedMessage = "404 NOT_FOUND \"" + String.format(COULD_NOT_FIND_COUNTRY_CODE, dummyCode) + "\"";
        String actualMessage = exception.getMessage();

        //Assert
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    private CountryCovidStats createCountryCovidStats() {
        CountryCovidStats countryCovidStats = new CountryCovidStats();
        countryCovidStats.setCountry("Dummy");
        countryCovidStats.setCountryCode("DM");
        return countryCovidStats;
    }
}
