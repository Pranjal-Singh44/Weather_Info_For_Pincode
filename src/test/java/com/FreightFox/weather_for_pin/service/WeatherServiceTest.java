package com.FreightFox.weather_for_pin.service;

import com.FreightFox.weather_for_pin.dto.WeatherRequestDTO;
import com.FreightFox.weather_for_pin.dto.WeatherResponseDTO;
import com.FreightFox.weather_for_pin.model.PincodeInfo;
import com.FreightFox.weather_for_pin.model.WeatherData;
import com.FreightFox.weather_for_pin.repository.PincodeInfoRepository;
import com.FreightFox.weather_for_pin.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private PincodeInfoRepository pincodeInfoRepository;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // ✅ Initializes mocks and injects them
    }

    @Test
    void shouldReturnCachedWeatherData_WhenAvailableInDB() {
        // given
        String pincode = "411014";
        LocalDate date = LocalDate.of(2024, 7, 7);
        WeatherRequestDTO request = new WeatherRequestDTO();
        request.setPincode(pincode);
        request.setForDate(date);

        PincodeInfo info = PincodeInfo.builder()
                .pincode(pincode)
                .city("Pune")
                .latitude(18.5685)
                .longitude(73.9158)
                .build();

        WeatherData data = WeatherData.builder()
                .pincode(pincode)
                .date(date)
                .temperature(32.0)
                .humidity(60)
                .description("clear sky")
                .build();

        when(weatherDataRepository.findByPincodeAndDate(pincode, date)).thenReturn(Optional.of(data));
        when(pincodeInfoRepository.findById(pincode)).thenReturn(Optional.of(info));

        // when
        WeatherResponseDTO response = weatherService.getWeatherForPincode(request);

        // then
        assertThat(response.getTemperature()).isEqualTo(32.0);
        assertThat(response.getHumidity()).isEqualTo(60);
        assertThat(response.getDescription()).isEqualTo("clear sky");
        assertThat(response.getPincode()).isEqualTo(pincode);

        verify(weatherDataRepository).findByPincodeAndDate(pincode, date);
        verify(pincodeInfoRepository).findById(pincode);
    }
}
