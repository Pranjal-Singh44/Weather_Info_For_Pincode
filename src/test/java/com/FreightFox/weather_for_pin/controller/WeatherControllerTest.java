package com.FreightFox.weather_for_pin.controller;

import com.FreightFox.weather_for_pin.dto.WeatherRequestDTO;
import com.FreightFox.weather_for_pin.dto.WeatherResponseDTO;
import com.FreightFox.weather_for_pin.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnWeatherResponse_whenValidRequest() throws Exception {
        WeatherRequestDTO request = new WeatherRequestDTO();
        request.setPincode("411014");
        request.setForDate(LocalDate.of(2024, 7, 7));

        WeatherResponseDTO response = WeatherResponseDTO.builder()
                .pincode("411014")
                .city("Pune")
                .latitude(18.5685)
                .longitude(73.9158)
                .date("2024-07-07")
                .temperature(32.5)
                .humidity(58)
                .description("partly cloudy")
                .build();

        Mockito.when(weatherService.getWeatherForPincode(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pincode").value("411014"))
                .andExpect(jsonPath("$.temperature").value(32.5))
                .andExpect(jsonPath("$.description").value("partly cloudy"));
    }
}



//package com.FreightFox.weather_for_pin.controller;
//
//import com.FreightFox.weather_for_pin.dto.WeatherRequestDTO;
//import com.FreightFox.weather_for_pin.dto.WeatherResponseDTO;
//import com.FreightFox.weather_for_pin.exception.CustomException;
//import com.FreightFox.weather_for_pin.service.WeatherService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SuppressWarnings("removal")
//@WebMvcTest(WeatherController.class)
//public class WeatherControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private WeatherService weatherService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void shouldReturnWeatherResponse_whenValidRequest() throws Exception {
//        WeatherRequestDTO request = new WeatherRequestDTO();
//        request.setPincode("411014");
//        request.setForDate(LocalDate.of(2024, 7, 7));
//
//        WeatherResponseDTO response = WeatherResponseDTO.builder()
//                .pincode("411014")
//                .city("Pune")
//                .latitude(18.5685)
//                .longitude(73.9158)
//                .date("2024-07-07")
//                .temperature(32.5)
//                .humidity(58)
//                .description("partly cloudy")
//                .build();
//
//        Mockito.when(weatherService.getWeatherForPincode(Mockito.any()))
//                .thenReturn(response);
//
//        mockMvc.perform(post("/api/weather")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pincode").value("411014"))
//                .andExpect(jsonPath("$.temperature").value(32.5))
//                .andExpect(jsonPath("$.description").value("partly cloudy"));
//    }
//
//    @Test
//    void shouldReturnBadRequest_whenServiceThrowsCustomException() throws Exception {
//        WeatherRequestDTO request = new WeatherRequestDTO();
//        request.setPincode("000000"); // Invalid/non-existent pincode
//        request.setForDate(LocalDate.of(2024, 7, 7));
//
//        Mockito.when(weatherService.getWeatherForPincode(Mockito.any()))
//                .thenThrow(new CustomException("Invalid pincode", HttpStatus.BAD_REQUEST));
//
//        mockMvc.perform(post("/api/weather")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Invalid pincode"));
//    }
//}
//
