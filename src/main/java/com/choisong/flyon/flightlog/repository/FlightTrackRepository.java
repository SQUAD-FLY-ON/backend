package com.choisong.flyon.flightlog.repository;

import com.choisong.flyon.flightlog.entity.FlightTrack;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FlightTrackRepository extends MongoRepository<FlightTrack, String> {
    Optional<FlightTrack> findByFlightLogIdAndMemberId(String flightLogId, Long memberId);
}
