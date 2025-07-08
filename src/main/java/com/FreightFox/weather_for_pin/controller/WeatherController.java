package com.FreightFox.weather_for_pin.controller;

import com.FreightFox.weather_for_pin.dto.WeatherRequestDTO;
import com.FreightFox.weather_for_pin.dto.WeatherResponseDTO;
import com.FreightFox.weather_for_pin.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping
    public ResponseEntity<WeatherResponseDTO> getWeather(@RequestBody WeatherRequestDTO request) {
        WeatherResponseDTO response = weatherService.getWeatherForPincode(request);
        return ResponseEntity.ok(response);
    }
}
