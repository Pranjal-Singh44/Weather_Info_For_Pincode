package com.FreightFox.weather_for_pin.service;

import com.FreightFox.weather_for_pin.dto.WeatherRequestDTO;
import com.FreightFox.weather_for_pin.dto.WeatherResponseDTO;
import com.FreightFox.weather_for_pin.exception.CustomException;
import com.FreightFox.weather_for_pin.model.PincodeInfo;
import com.FreightFox.weather_for_pin.model.WeatherData;
import com.FreightFox.weather_for_pin.repository.PincodeInfoRepository;
import com.FreightFox.weather_for_pin.repository.WeatherDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final PincodeInfoRepository pincodeInfoRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.geocode.url}")
    private String geoBaseUrl;

    @Value("${weather.api.weather.url}")
    private String weatherBaseUrl;

    @Transactional
    public WeatherResponseDTO getWeatherForPincode(WeatherRequestDTO request) {
        String pincode = request.getPincode();
        LocalDate date = request.getForDate();

        // Check cache
        Optional<WeatherData> cached = weatherDataRepository.findByPincodeAndDate(pincode, date);
        if (cached.isPresent()) {
            WeatherData data = cached.get();
            PincodeInfo info = pincodeInfoRepository.findById(pincode)
                    .orElseThrow(() -> new CustomException("Pincode data missing for " + pincode));
            return toDTO(info, data);
        }

        // Step 1: Get lat/lon using Geocoding API
        String geoUrl = String.format("%s?zip=%s,IN&appid=%s", geoBaseUrl, pincode, apiKey);
        var geoResponse = restTemplate.getForObject(geoUrl, GeoResponse.class);
        if (geoResponse == null) {
            throw new CustomException("Failed to retrieve location data.");
        }

        // Save PincodeInfo if not already stored
        PincodeInfo info = pincodeInfoRepository.findById(pincode)
                .orElseGet(() -> {
                    PincodeInfo newInfo = PincodeInfo.builder()
                            .pincode(pincode)
                            .latitude(geoResponse.lat)
                            .longitude(geoResponse.lon)
                            .city(geoResponse.name)
                            .build();
                    return pincodeInfoRepository.save(newInfo);
                });

        // Step 2: Get current weather
        String weatherUrl = String.format("%s?lat=%f&lon=%f&units=metric&appid=%s",
                weatherBaseUrl, info.getLatitude(), info.getLongitude(), apiKey);
        var weatherResponse = restTemplate.getForObject(weatherUrl, WeatherAPIResponse.class);
        if (weatherResponse == null) {
            throw new CustomException("Failed to retrieve weather data.");
        }

        // Save weather data
        WeatherData weatherData = WeatherData.builder()
                .pincode(pincode)
                .date(date)
                .temperature(weatherResponse.main.temp)
                .humidity(weatherResponse.main.humidity)
                .description(weatherResponse.weather[0].description)
                .build();
        weatherDataRepository.save(weatherData);

        return toDTO(info, weatherData);
    }

    private WeatherResponseDTO toDTO(PincodeInfo info, WeatherData data) {
        return WeatherResponseDTO.builder()
                .pincode(info.getPincode())
                .latitude(info.getLatitude())
                .longitude(info.getLongitude())
                .city(info.getCity())
                .date(data.getDate().toString())
                .temperature(data.getTemperature())
                .humidity(data.getHumidity())
                .description(data.getDescription())
                .build();
    }

    // --- Helper Classes to Map API JSON ---

    private static class GeoResponse {
        public String name;
        public double lat;
        public double lon;
    }

    private static class WeatherAPIResponse {
        public Main main;
        public Weather[] weather;

        static class Main {
            public double temp;
            public int humidity;
        }

        static class Weather {
            public String description;
        }
    }
}
