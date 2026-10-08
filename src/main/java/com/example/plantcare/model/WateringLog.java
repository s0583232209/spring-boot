package com.example.plantcare.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name = "watering_logs")
@Getter @Setter @NoArgsConstructor
public class WateringLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plant_id")
    private Plant plant;
    @Column(nullable = false) private Instant wateredAt = Instant.now();
    @Column(length = 200) private String note;
}