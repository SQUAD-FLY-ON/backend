package com.choisong.flyon.schedule.service;

import com.choisong.flyon.gpt.service.GptService;
import com.choisong.flyon.paraglidingspot.dto.SpotResponse;
import com.choisong.flyon.paraglidingspot.service.ParaglidingSpotService;
import com.choisong.flyon.schedule.domain.TourismSchedule;
import com.choisong.flyon.schedule.dto.ScheduleCreateRequest;
import com.choisong.flyon.schedule.dto.ScheduleListResponse;
import com.choisong.flyon.schedule.dto.ScheduleRecommendRequest;
import com.choisong.flyon.schedule.dto.ScheduleRecommendResponse;
import com.choisong.flyon.schedule.repository.TourismScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
@Transactional
public class TourismScheduleService {

    private final TourismScheduleRepository tourismScheduleRepository;
    private final GptService gptService;
    private final ParaglidingSpotService paraglidingSpotService;
    private final ObjectMapper objectMapper;

    public ScheduleRecommendResponse createGptSchedule(@RequestBody ScheduleRecommendRequest request) {
        SpotResponse paraglidingSpot = paraglidingSpotService.findById(request.paraglidingSpotId());
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
            throw new RuntimeException("스케줄 JSON 파싱 실패", e);
        }
    }

    public void createSchedule(final ScheduleCreateRequest request, final Long memberId) {
        TourismSchedule tourismSchedule = TourismSchedule.builder()
            .memberId(memberId)
            .dailyTourismSpots(request.schedules())
            .scheduleStart(request.scheduleStart())
            .scheduleEnd(request.scheduleEnd())
            .build();
        tourismScheduleRepository.save(tourismSchedule);
    }

    public ScheduleListResponse getSchedule(final Long memberId) {
        return new ScheduleListResponse(tourismScheduleRepository.findSchedulesByMemberId(memberId));
    }
}
