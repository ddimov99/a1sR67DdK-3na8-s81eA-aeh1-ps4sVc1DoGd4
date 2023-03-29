package com.proect.covidstats.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "country_covid_stats")
public class CountryCovidStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idc;

    private String countryId;

    private String country;

    private String countryCode;

    private String slug;

    private int newConfirmed;

    private long totalConfirmed;

    private int newDeaths;

    private long totalDeaths;

    private int newRecovered;

    private long totalRecovered;

    private LocalDateTime date;
}
