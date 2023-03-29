package com.proect.covidstats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proect.covidstats.model.dto.CountryCovidStatsDto;
import com.proect.covidstats.service.CovidStatsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CovidStatsController.class)
public class CovidStatsControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    CovidStatsService covidStatsService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void givenHttpGet_whenGetCountryStatsByCountryCode_thenVerifyValues() throws Exception {
        CountryCovidStatsDto mockCountryCovidStatsDto = createCountryCovidStatsDto();
        String mockCountryCode = "DM";
        ObjectMapper om = new ObjectMapper();

        Mockito.when(covidStatsService.getCountryCovidStatsByCountryCode(mockCountryCode)).thenReturn(mockCountryCovidStatsDto);

        //Act & Assert
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/covid-stats/country/DM"))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        CountryCovidStatsDto actual = om.readValue(resultContent, CountryCovidStatsDto.class);
        Assertions.assertEquals(mockCountryCovidStatsDto, actual);
    }

    private CountryCovidStatsDto createCountryCovidStatsDto() {
        CountryCovidStatsDto countryCovidStatsDto = new CountryCovidStatsDto();
        countryCovidStatsDto.setCountry("test");
        countryCovidStatsDto.setCountryCode("test");
        return countryCovidStatsDto;
    }
}
