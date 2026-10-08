package com.example.plantcare.controller;

import com.example.plantcare.dto.WeatherInfo;
import com.example.plantcare.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/weather") @RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weather;

    @GetMapping("/jerusalem")
    public ResponseEntity<WeatherInfo> jerusalem() {
        return weather.today().map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }
}