package com.choisong.flyon.schedule.repository;

import com.choisong.flyon.flightlog.entity.FlightTrack;
import com.choisong.flyon.schedule.domain.TourismSchedule;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TourismScheduleRepository extends MongoRepository<TourismSchedule, String> {

}
