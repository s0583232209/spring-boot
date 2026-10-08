package com.example.plantcare.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity @Table(name = "plants")
@Getter @Setter @NoArgsConstructor
public class Plant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60) private String name;
    @Column(length = 60) private String species;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private PlantLocation location;
    @Column(nullable = false) private int wateringIntervalDays;
    @Column(nullable = false)
    private LocalDate lastWateredAt = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
}