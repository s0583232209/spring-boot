package com.example.plantcare.dto;
import com.example.plantcare.model.*;
import java.time.LocalDate;

public record PlantResponse(Long id, String name, String species, PlantLocation location,
                            int wateringIntervalDays, LocalDate lastWateredAt, PlantStatus status, String advice) {}