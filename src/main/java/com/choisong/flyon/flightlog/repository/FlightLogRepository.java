package com.choisong.flyon.flightlog.repository;

import com.choisong.flyon.flightlog.entity.FlightLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlightLogRepository extends MongoRepository<FlightLog, String> {
    List<FlightLog> findByMemberId(Long memberId);
}