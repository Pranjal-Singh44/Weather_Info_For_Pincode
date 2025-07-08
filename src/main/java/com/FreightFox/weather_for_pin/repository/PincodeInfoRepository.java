package com.FreightFox.weather_for_pin.repository;

import com.FreightFox.weather_for_pin.model.PincodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeInfoRepository extends JpaRepository<PincodeInfo, String> {
    // no additional methods needed for now
}
