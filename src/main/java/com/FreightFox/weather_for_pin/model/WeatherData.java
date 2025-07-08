package com.FreightFox.weather_for_pin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "weather_data", indexes= {
		@Index(columnList="id,pincode,date,temperature,humidity,description")
})
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private LocalDate date;
    private double temperature;
    private int humidity;
    private String description;
}



//package com.FreightFox.weather_for_pin.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "weather_data")
//public class WeatherData {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String pincode;
//
//    @Column(nullable = false)
//    private LocalDate date;
//
//    @Column(nullable = false)
//    private Double temperature;
//
//    @Column(nullable = false)
//    private Integer humidity;
//
//    @Column(nullable = false)
//    private String description;
//}
