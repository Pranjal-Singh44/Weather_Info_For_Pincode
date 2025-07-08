package com.FreightFox.weather_for_pin.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PincodeInfo {

    @Id
    private String pincode;

    private String city;
    private double latitude;
    private double longitude;
}

