package com.choisong.flyon.weather.repository;

import com.choisong.flyon.weather.domain.Weather;
import com.choisong.flyon.weather.dto.SidoWeahterResponse.SigunguWeather;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findAllBySido(String sido);
}
