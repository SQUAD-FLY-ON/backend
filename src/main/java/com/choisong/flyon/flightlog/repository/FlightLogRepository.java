package com.choisong.flyon.flightlog.repository;

import com.choisong.flyon.flightlog.entity.FlightLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FlightLogRepository extends MongoRepository<FlightLog, String> {
    Page<FlightLog> findByMemberId(Long memberId, Pageable pageable);
}