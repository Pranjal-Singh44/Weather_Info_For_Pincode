package com.FreightFox.weather_for_pin.repository;

import com.FreightFox.weather_for_pin.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByPincodeAndDate(String pincode, LocalDate date);
}

