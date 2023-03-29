package com.proect.covidstats.repository;

import com.proect.covidstats.model.entity.CountryCovidStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryCovidStatsRepository extends JpaRepository<CountryCovidStats, Long> {

    Optional<CountryCovidStats> findCountryCovidStatsByCountryCode(String countryCode);
}
