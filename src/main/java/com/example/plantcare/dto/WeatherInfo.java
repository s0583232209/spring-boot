package com.example.plantcare.dto;
import com.example.plantcare.service.WeatherService;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record WeatherInfo(
        LocalDate date,
        @JsonProperty("max_temp_c") double maxC,
        @JsonProperty("min_temp_c") double minC,
        @JsonProperty("rain_mm") double rainMm
) {}
