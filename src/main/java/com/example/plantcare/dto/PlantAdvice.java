package com.example.plantcare.dto;
import com.example.plantcare.model.PlantStatus;

public record PlantAdvice(PlantStatus status, String message) {}

