package com.example.plantcare.repository;

import com.example.plantcare.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {}