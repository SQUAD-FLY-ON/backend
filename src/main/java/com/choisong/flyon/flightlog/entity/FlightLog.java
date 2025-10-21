package com.choisong.flyon.flightlog.entity;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "flight_logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightLog {

    @Id
    private String id;

    private Long memberId;

    private String airfieldName;

    private String airfieldImageUrl;

    private double flightTime;

    private double flightDistance;

    private double averageSpeed;

    private int flightAltitude;

    private LocalDateTime createdAt;
}