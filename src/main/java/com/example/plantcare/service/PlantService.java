package com.example.plantcare.service;

import com.example.plantcare.dto.*;
import com.example.plantcare.exception.ResourceNotFoundException;
import com.example.plantcare.model.*;
import com.example.plantcare.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class PlantService {
    private static final ZoneId JLM = ZoneId.of("Asia/Jerusalem");
    private final PlantRepository plants;
    private final WateringLogRepository logs;
    private final WeatherService weather;
    private final CareAdvisor advisor;

    public PlantResponse create(CreatePlantRequest r) {
        Plant p = new Plant();
        p.setName(r.name()); p.setSpecies(r.species());
        p.setLocation(r.location()); p.setWateringIntervalDays(r.wateringIntervalDays());
        return toResponse(plants.save(p), weather.today());
    }

    @Transactional(readOnly = true)
    public List<PlantResponse> findAll(PlantStatus status) {
        var w = weather.today();                       // קריאה אחת לכל הצמחים
        return plants.findAll().stream().map(p -> toResponse(p, w))
                .filter(r -> status == null || r.status() == status).toList();
    }

    @Transactional(readOnly = true)
    public PlantResponse findById(Long id) { return toResponse(get(id), weather.today()); }

    public PlantResponse water(Long id, String note) {
        Plant p = get(id);
        p.setLastWateredAt(LocalDate.now(JLM));
        WateringLog l = new WateringLog();
        l.setPlant(p); l.setNote(note);
        logs.save(l);
        return toResponse(p, weather.today());
    }

    @Transactional(readOnly = true)
    public List<WateringLogResponse> history(Long id) {
        get(id);   // 404 אם הצמח לא קיים
        return logs.findByPlantIdOrderByWateredAtDesc(id).stream()
                .map(l -> new WateringLogResponse(l.getWateredAt(), l.getNote())).toList();
    }

    private Plant get(Long id) {
        return plants.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant " + id + " not found"));
    }

    private PlantResponse toResponse(Plant p, Optional<WeatherInfo> w) {
        PlantAdvice a = advisor.advise(p, LocalDate.now(JLM), w);
        return new PlantResponse(p.getId(), p.getName(), p.getSpecies(), p.getLocation(),
                p.getWateringIntervalDays(), p.getLastWateredAt(), a.status(), a.message());
    }
}