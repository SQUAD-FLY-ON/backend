package com.choisong.flyon.flightlog.repository;

import com.choisong.flyon.flightlog.entity.FlightTrack;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightTrackRepository extends MongoRepository<FlightTrack, String> {

    Optional<FlightTrack> findByFlightLogIdAndMemberId(String flightLogId, Long memberId);

    Optional<FlightTrack> findByParaglidingSpotId(Long paraglidingSpotId);
}
