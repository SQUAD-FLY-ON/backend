package com.choisong.flyon.flightlog.service;

import com.choisong.flyon.flightlog.dto.FlightLogRequest;
import com.choisong.flyon.flightlog.dto.FlightLogResponse;
import com.choisong.flyon.flightlog.exception.FlightLogAccessDeniedException;
import com.choisong.flyon.flightlog.exception.FlightLogNotFoundException;
import com.choisong.flyon.flightlog.mapper.FlightLogMapper;
import com.choisong.flyon.flightlog.repository.FlightLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightLogService {

    private final FlightLogRepository flightLogRepository;
    private final FlightLogMapper flightLogMapper;

    public FlightLogResponse create(FlightLogRequest request, Long memberId) {
        var entity = flightLogMapper.toEntity(request, memberId);
        var saved = flightLogRepository.save(entity);
        return flightLogMapper.toResponse(saved);
    }

    public List<FlightLogResponse> getByMemberId(Long memberId) {
        var flightLogs = flightLogRepository.findByMemberId(memberId);

        if (flightLogs.isEmpty()) {
            throw FlightLogNotFoundException.notFound();
        }

        return flightLogs.stream()
                .map(flightLogMapper::toResponse)
                .toList();
    }

    public void delete(String id, Long memberId) {
        var flightLog = flightLogRepository.findById(id)
                .orElseThrow(FlightLogNotFoundException::notFound);

        if (!flightLog.getMemberId().equals(memberId)) {
            throw FlightLogAccessDeniedException.accessDenied();
        }

        flightLogRepository.deleteById(id);
    }
}