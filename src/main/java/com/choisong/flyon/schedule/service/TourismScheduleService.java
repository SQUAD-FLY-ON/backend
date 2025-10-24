package com.choisong.flyon.schedule.service;

import com.choisong.flyon.gpt.service.GptService;
import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.SpotResponse;
import com.choisong.flyon.paraglidingspot.service.ParaglidingSpotService;
import com.choisong.flyon.schedule.domain.TourismSchedule;
import com.choisong.flyon.schedule.domain.TourismSchedule.TourismSpot;
import com.choisong.flyon.schedule.dto.ScheduleCreateRequest;
import com.choisong.flyon.schedule.dto.ScheduleListResponse;
import com.choisong.flyon.schedule.dto.ScheduleRecommendRequest;
import com.choisong.flyon.schedule.dto.ScheduleRecommendResponse;
import com.choisong.flyon.schedule.repository.TourismScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Transactional
public class TourismScheduleService {

    private final TourismScheduleRepository tourismScheduleRepository;
    private final GptService gptService;
    private final ParaglidingSpotService paraglidingSpotService;
    private final ObjectMapper objectMapper;

    public ScheduleRecommendResponse createGptSchedule(@RequestBody ScheduleRecommendRequest request) {
        SpotResponse paraglidingSpot = paraglidingSpotService.findSpotResponseById(request.paraglidingSpotId());
        try {
            String paraglidingSpotJson = objectMapper.writeValueAsString(paraglidingSpot);
            String tourismSpotJson = objectMapper.writeValueAsString(request.tourismSpotList());

            ChatResponse schedule = gptService.createSchedule(
                    paraglidingSpotJson,
                    tourismSpotJson,
                    request.scheduleStart().toString(),
                    request.scheduleEnd().toString()
            );

            String jsonText = schedule.getResult().getOutput().getText();

            return parseScheduleJson(jsonText);

        } catch (Exception e) {
            e.printStackTrace();
            return new ScheduleRecommendResponse(Collections.emptyList());
        }
    }

    public ScheduleRecommendResponse parseScheduleJson(String json) {
        try {
            return objectMapper.readValue(json, ScheduleRecommendResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "스케줄 JSON 파싱 실패", e);
        }
    }

    public void createSchedule(final ScheduleCreateRequest request, final Long memberId) {
        TourismSpot tourismSpot = request.schedules().stream()
                .flatMap(List::stream)
                .filter(spot -> spot.getId() != null)
                .findFirst()
                .get();
        ParaglidingSpot spot = paraglidingSpotService.findById(tourismSpot.getId());
        TourismSchedule tourismSchedule = TourismSchedule.builder()
                .memberId(memberId)
                .dailyTourismSpots(request.schedules())
                .scheduleStart(request.scheduleStart())
                .scheduleEnd(request.scheduleEnd())
                .tourName(spot.getAddress().getSigungu())
                .build();
        tourismScheduleRepository.save(tourismSchedule);
    }

    public ScheduleListResponse getSchedule(final Long memberId) {
        LocalDate today = LocalDate.now();

        List<TourismSchedule> activeOrUpcoming = tourismScheduleRepository.findActiveOrUpcomingSchedules(memberId, today);
        List<TourismSchedule> past = tourismScheduleRepository.findPastSchedules(memberId, today);

        List<TourismSchedule> sorted = new ArrayList<>();
        sorted.addAll(activeOrUpcoming);
        sorted.addAll(past);

        return new ScheduleListResponse(sorted);
    }


    public TourismSchedule getScheduleById(final String id, final Long memberId) {
        TourismSchedule schedule = tourismScheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        if (!schedule.getMemberId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return schedule;
    }

    public void deleteSchedule(final String id, final Long memberId) {
        TourismSchedule schedule = tourismScheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        if (!schedule.getMemberId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        tourismScheduleRepository.deleteById(id);
    }
}