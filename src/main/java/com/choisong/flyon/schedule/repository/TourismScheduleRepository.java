package com.choisong.flyon.schedule.repository;

import com.choisong.flyon.schedule.domain.TourismSchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TourismScheduleRepository extends MongoRepository<TourismSchedule, String> {

    @Query(value = "{ 'memberId': ?0, 'scheduleEnd': { $gte: ?1 } }", sort = "{ 'scheduleStart': 1 }")
    List<TourismSchedule> findActiveOrUpcomingSchedules(Long memberId, LocalDate today);

    @Query(value = "{ 'memberId': ?0, 'scheduleEnd': { $lt: ?1 } }", sort = "{ 'scheduleStart': -1 }")
    List<TourismSchedule> findPastSchedules(Long memberId, LocalDate today);

}
