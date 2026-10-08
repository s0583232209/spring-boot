package com.example.plantcare.dto;
import com.example.plantcare.model.PlantLocation;
import jakarta.validation.constraints.*;

public record CreatePlantRequest(
        @NotBlank @Size(max = 60) String name,
        @Size(max = 60) String species,
        @NotNull PlantLocation location,
        @NotNull @Min(1) @Max(60) Integer wateringIntervalDays) {}