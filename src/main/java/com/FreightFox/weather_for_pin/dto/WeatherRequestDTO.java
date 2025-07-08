package com.FreightFox.weather_for_pin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherRequestDTO {
    private String pincode;
    private LocalDate forDate;
}

