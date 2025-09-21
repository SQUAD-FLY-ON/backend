package com.choisong.flyon.gpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi.ChatModel;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptService {

    private final OpenAiChatModel openApiChatModel;

    String promptCommand = """
        너는 여행 일정 계획 전문가다. 
        내가 주는 데이터는 관광지 목록, 패러글라이딩 체험장, 여행 시작일과 종료일이다. 
        너는 위도/경도를 활용하여 일별 최적의 여행 경로를 만들어야 한다. 
        
        ### 반드시 지켜야 할 조건
        1. 패러글라이딩 체험장은 전체 여행 일정 중 하루만 포함해야 한다. 
           - 가급적 여행 초반 일정에 배치한다. (필수는 아님, 날짜와 위치에 맞는 기상, 날씨를 참고해서 지정하도록!)
        2. 내가 제공한 관광지 데이터 목록은 중복 없이 전부 일정에 포함해야 한다. 
        3. 여행 기간(`scheduleStart`, `scheduleEnd`)을 기준으로 바깥 배열 크기를 계산한다. 
           - 예: 시작=9/1, 종료=9/3 → 바깥 배열 크기=3 (Day1~Day3) 
           - 특정 날짜에 일정이 없어도 빈 배열을 유지한다. 
        4. 내부 배열의 순서는 이동 경로이다. 
        5. 각 날짜별로 관광지가 고르게 분포되도록 나눈다. 
        6. 반환 형식은 JSON 객체이고, key 이름은 **schedules** 로 한다. 
        7. 배열 안의 데이터 형식은 내가 제공한 구조를 반드시 따른다. 
           - 관광지 예시:
             {
               "id": null,
               "tourismType": "ATTRACTION_SPOT",
               "name": "2024 서울야외도서관 : 책읽는 서울광장",
               "fullAddress": "서울특별시 중구 세종대로 110 (태평로1가)",
               "longitude": "126.9777210995",
               "latitude": "37.5662570431",
               "phoneNumber": "02-2088-4552",
               "imgUrl": "http://tong.visitkorea.or.kr/cms/resource/00/3312500_image2_1.jpg"
             }
           - 패러글라이딩 예시:
             {
               "id": "51",
               "tourismType": "PARAGLIDING_SPOT",
               "name": "단양청춘패러",
               "fullAddress": "충청북도 단양군 가곡면 두산길 262",
               "longitude": 128.40072,
               "latitude": 37.0022414,
               "phoneNumber": "02-2088-4552",
               "imgUrl": ""
             }
           - 주의: 만약 id 값이 null 이 아니라면 tourismType은 "PARAGLIDING_SPOT"으로 지정한다. 
        ### 중요 : 내부 빈 배열을 허용하지만 되도록이면 모든 일정에 관광지를 배분하려고 노력해라.
        ### 반환 규칙
        - 무조건 JSON 객체만 반환해야 한다. (설명 X) 
        - 최상위 구조는:
          {
            "schedules": [
              [ Day1 일정 ],
              [ Day2 일정 ],
              ...
            ]
          }
        예시는 예시일 뿐이고 실제 데이터를 참고하도록
        
        ---
        관광지 데이터 목록:
        %s
        
        패러글라이딩 체험장:
        %s
        
        여행 시작일:
        %s
        
        여행 종료일:
        %s
        """;

    public ChatResponse createSchedule(final String paraglidingSpotJson, final String tourismSpotJson,
        final String scheduleStart, final String scheduleEnd) {
        final Prompt prompt = new Prompt(
            this.promptCommand.formatted(tourismSpotJson, paraglidingSpotJson, scheduleStart, scheduleEnd),
            OpenAiChatOptions.builder()
                .model(ChatModel.GPT_4_O) // 최신 모델로 교체 가능
                .responseFormat(
                    ResponseFormat.builder()
                        .type(ResponseFormat.Type.JSON_OBJECT)
                        .build()
                )
                .build());
        return this.openApiChatModel.call(prompt);
    }
}
