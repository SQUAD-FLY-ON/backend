package com.choisong.flyon.flightlog.service;

import com.choisong.flyon.flightlog.dto.FlightTrackResponse;
import com.choisong.flyon.flightlog.dto.FlightTrackUpsertRequest;
import com.choisong.flyon.flightlog.dto.TrackPointDto;
import com.choisong.flyon.flightlog.entity.FlightLog;
import com.choisong.flyon.flightlog.entity.FlightTrack;
import com.choisong.flyon.flightlog.exception.FlightLogAccessDeniedException;
import com.choisong.flyon.flightlog.exception.FlightLogNotFoundException;
import com.choisong.flyon.flightlog.repository.FlightLogRepository;
import com.choisong.flyon.flightlog.repository.FlightTrackRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightTrackService {

    private final FlightLogRepository flightLogRepository;
    private final FlightTrackRepository flightTrackRepository;

    private static void set(Object target, String name, Object value) {
        try {
            var f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception ignored) {
        }
    }

    public void upsert(String flightLogId, Long memberId, FlightTrackUpsertRequest req) {
        var log = mustOwnLog(flightLogId, memberId);
        var points = toPoints(req.points());

        var track = flightTrackRepository.findByFlightLogIdAndMemberId(flightLogId, memberId)
            .orElse(FlightTrack.builder()
                .flightLogId(log.getId())
                .memberId(memberId)
                .build());

        set(track, "points", points);
        set(track, "updatedAt", LocalDateTime.now());
        flightTrackRepository.save(track);
    }

    public FlightTrackResponse get(String flightLogId, Long memberId) {
        mustOwnLog(flightLogId, memberId);
        var track = flightTrackRepository.findByFlightLogIdAndMemberId(flightLogId, memberId)
            .orElseGet(() -> FlightTrack.builder().points(List.of()).build());
        var dtos = track.getPoints() == null ? List.<TrackPointDto>of()
            : track.getPoints().stream()
                .map(p -> new TrackPointDto(p.getLatitude(), p.getLongitude(), p.getAltitude(), p.getTime()))
                .toList();
        return new FlightTrackResponse(dtos);
    }

    public FlightTrackResponse getByParaglidingSpotId(Long paraglidingSpotId) {
        var track = flightTrackRepository.findByParaglidingSpotId(paraglidingSpotId)
            .orElseGet(() -> FlightTrack.builder().points(List.of()).build());
        var dtos = track.getPoints() == null ? List.<TrackPointDto>of()
            : track.getPoints().stream()
                .map(p -> new TrackPointDto(p.getLatitude(), p.getLongitude(), p.getAltitude(), p.getTime()))
                .toList();
        return new FlightTrackResponse(dtos);
    }

    public List<TrackPointDto> getPoints(String flightLogId, Long memberId) {
        mustOwnLog(flightLogId, memberId);
        var track = flightTrackRepository.findByFlightLogIdAndMemberId(flightLogId, memberId)
            .orElseGet(() -> FlightTrack.builder().points(List.of()).build());

        return track.getPoints() == null ? List.of()
            : track.getPoints().stream()
                .map(p -> new TrackPointDto(p.getLatitude(), p.getLongitude(), p.getAltitude(), p.getTime()))
                .toList();
    }

    private FlightLog mustOwnLog(String id, Long memberId) {
        var log = flightLogRepository.findById(id)
            .orElseThrow(FlightLogNotFoundException::notFound);
        if (!log.getMemberId().equals(memberId)) {
            throw FlightLogAccessDeniedException.accessDenied();
        }
        return log;
    }

    private List<FlightTrack.TrackPoint> toPoints(List<TrackPointDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream()
            .map(d -> FlightTrack.TrackPoint.builder()
                .latitude(d.latitude())
                .longitude(d.longitude())
                .altitude(d.altitude())
                .time(d.time())
                .build())
            .toList();
    }
}
