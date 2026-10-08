package com.example.plantcare.dto;
import java.time.Instant;

public record WateringLogResponse(Instant wateredAt, String note) {}