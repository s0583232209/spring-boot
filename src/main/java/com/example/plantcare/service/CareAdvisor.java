package com.example.plantcare.service;

import com.example.plantcare.dto.*;
import com.example.plantcare.model.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class CareAdvisor {

    public PlantAdvice advise(Plant p, LocalDate today, Optional<WeatherInfo> weather) {
        int interval = p.getWateringIntervalDays();
        String note = "";

        if (p.getLocation() == PlantLocation.OUTDOOR && weather.isPresent()) {
            WeatherInfo w = weather.get();
            if (w.rainMm() >= 2)
                return new PlantAdvice(PlantStatus.OK, "Rain today (" + w.rainMm() + " mm), skip watering.");
            if (w.maxC() >= 35) {
                interval = Math.max(1, interval - 1);
                note += " Heat wave (" + w.maxC() + "C): watering more often.";
            }
            if (w.minC() <= 2) note += " Frost risk tonight: cover the plant.";
        }

        long days = ChronoUnit.DAYS.between(p.getLastWateredAt(), today);
        PlantStatus status = days < interval ? PlantStatus.OK
                : days == interval ? PlantStatus.THIRSTY : PlantStatus.OVERDUE;
        return new PlantAdvice(status, "Last watered " + days + " days ago." + note);
    }
}