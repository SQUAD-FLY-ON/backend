package com.choisong.flyon.flightlog.mapper;

import com.choisong.flyon.flightlog.dto.FlightLogRequest;
import com.choisong.flyon.flightlog.dto.FlightLogResponse;
import com.choisong.flyon.flightlog.entity.FlightLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberId", source = "memberId")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    FlightLog toEntity(FlightLogRequest request, Long memberId);

    FlightLogResponse toResponse(FlightLog flightLog);
}