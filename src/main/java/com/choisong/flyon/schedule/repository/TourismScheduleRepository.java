package com.choisong.flyon.schedule.repository;

import com.choisong.flyon.schedule.domain.TourismSchedule;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TourismScheduleRepository extends MongoRepository<TourismSchedule, String> {

    @Query(value = "{ 'memberId': ?0 }", sort = "{ 'scheduleStart': -1 }")
    List<TourismSchedule> findSchedulesByMemberId(Long memberId);

}
