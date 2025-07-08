package com.FreightFox.weather_for_pin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDTO {
    private String pincode;
    private double latitude;
    private double longitude;
    private String city;
    private String date;
    private double temperature;
    private int humidity;
    private String description;
}

