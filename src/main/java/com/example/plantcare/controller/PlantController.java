package com.example.plantcare.controller;

import com.example.plantcare.dto.*;
import com.example.plantcare.model.PlantStatus;
import com.example.plantcare.service.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/plants") @RequiredArgsConstructor
public class PlantController {
    private final PlantService service;

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public PlantResponse create(@Valid @RequestBody CreatePlantRequest req) { return service.create(req); }

    @GetMapping
    public List<PlantResponse> list(@RequestParam(required = false) PlantStatus status) {
        return service.findAll(status);
    }

    @GetMapping("/{id}")
    public PlantResponse get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping("/{id}/water")
    public PlantResponse water(@PathVariable Long id, @RequestParam(required = false) String note) {
        return service.water(id, note);
    }

    @GetMapping("/{id}/logs")
    public List<WateringLogResponse> logs(@PathVariable Long id) { return service.history(id); }
}
