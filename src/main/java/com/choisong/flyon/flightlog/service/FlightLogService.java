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

    public FlightLogResponse create(FlightLogRequest request, Long memberId) {
        var entity = flightLogMapper.toEntity(request, memberId);
        var saved = flightLogRepository.save(entity);
        var member = memberService.getMemberById(memberId);
        member.increaseJumpAltitude(request.flightAltitude());
        return flightLogMapper.toResponse(saved);
    }

    public Slice<FlightLogResponse> getMyFlightLogs(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return flightLogRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable)
                .map(flightLogMapper::toResponse);
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