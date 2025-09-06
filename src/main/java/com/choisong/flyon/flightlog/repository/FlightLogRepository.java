package com.choisong.flyon.flightlog.repository;

import com.choisong.flyon.flightlog.entity.FlightLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FlightLogRepository extends MongoRepository<FlightLog, String> {

    Slice<FlightLog> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
}