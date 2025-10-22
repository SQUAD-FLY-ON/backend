package com.choisong.flyon.flightlog.service;

import com.choisong.flyon.flightlog.dto.FlightLogRequest;
import com.choisong.flyon.flightlog.dto.FlightLogResponse;
import com.choisong.flyon.flightlog.exception.FlightLogAccessDeniedException;
import com.choisong.flyon.flightlog.exception.FlightLogNotFoundException;
import com.choisong.flyon.flightlog.mapper.FlightLogMapper;
import com.choisong.flyon.flightlog.repository.FlightLogRepository;
import com.choisong.flyon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class FlightLogService {

    private final FlightLogRepository flightLogRepository;
    private final FlightLogMapper flightLogMapper;
    private final MemberService memberService;
    private final FlightTrackService flightTrackService;

    public FlightLogResponse create(FlightLogRequest request, Long memberId) {
        var entity = flightLogMapper.toEntity(request, memberId);
        var saved = flightLogRepository.save(entity);

        var member = memberService.getMemberById(memberId);
        member.increaseJumpAltitude(request.flightAltitude()); // 기존 메서드명 유지

//        if (request.points() != null && !request.points().isEmpty()) {
//            flightTrackService.upsert(
//                saved.getId(),
//                memberId,
//                new FlightTrackUpsertRequest(request.points())
//            );
//        }
        return flightLogMapper.toResponse(saved);
    }

    public Slice<FlightLogResponse> getMyFlightLogs(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return flightLogRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable)
                .map(flightLog -> {
                    var points = flightTrackService.getPoints(flightLog.getId(), memberId);
                    var dto = flightLogMapper.toResponse(flightLog);
                    // record 불변이라 새로 생성해 track 세팅
                    return new FlightLogResponse(
                            dto.id(),
                            dto.airfieldName(),
                            dto.airfieldImageUrl(),
                            dto.flightTime(),
                            dto.flightDistance(),
                            dto.averageSpeed(),
                            dto.flightAltitude(),
                            dto.createdAt(),
                            points
                    );
                });
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