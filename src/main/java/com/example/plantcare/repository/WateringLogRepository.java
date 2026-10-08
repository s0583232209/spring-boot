package com.example.plantcare.repository;

import com.example.plantcare.model.WateringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WateringLogRepository extends JpaRepository<WateringLog, Long> {
    List<WateringLog> findByPlantIdOrderByWateredAtDesc(Long plantId);
}