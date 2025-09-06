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
        넌 지금부터 여행 계획 수립 전문가야. 
        내가 주는 데이터는 관광지, 패러글라이딩 체험장, 여행 기간 정보를 줄테니 위도/경도를 계산하고 최적의 여행 경로를 일별로 수립해야해.
        아래 조건을 반드시 만족시켜야해.
        1. 패러글라이딩 체험장은 전체 여행 일정에 하루만 할당해야해. 되도록이면 여행 일정 중에서 앞쪽으로 배치해.
        2. 요청한 관광지 데이터 목록은 각 날짜에 중복없이 모두 포함시켜야해.
        4. 응답은 배열에 배열로 주고 배열의 인덱스를 날짜로 구분할거야.
        5. 내부 배열의 순서가 바로 너가 계획할 여행 경로가 되는거야.
        6. 내가 제공한 여행 시작 일 , 여행 종료일을 참고해서 각 날짜별로 데이터를 고르게 분류해.
        특정 날짜에 아무런 일정이 없어도 좋아, 예를들어 날짜를 계산한다고하면 시작일과 종료일도 여행 Day에 포함이야 9월10일부터 9월15일까지라면 Day1 부터 Day6까지 총 6일, 즉 바깥배열의 크기가 6이돼야겠지.
        제일 중요한 것은 날짜를 계산하고 그 날짜별로 고르게 데이터를 분포시키는 작업이야. 할 수 있지? 부탁좀하자 제발. 6일간 여행이면 배열크기 outer 배열 크기를 6으로 좀 하고 2일간 여행이면 outer 배열 크기를 2로 좀 해서 해봐...
        7. 배열안의 배열에 각 인덱스에 들어갈 관광지 데이터 예시 형식은 아래와 같아
                    {
                        "id":null
                        "tourismType:"ATTRACTION_SPOT"
                        "name": "2024 서울야외도서관 : 책읽는 서울광장",
                        "fullAddress": "서울특별시 중구 세종대로 110 (태평로1가)",
                        "longitude": "126.9777210995",
                        "latitude": "37.5662570431",
                        "phoneNumber": "02-2088-4552",
                        "imgUrl": "http://tong.visitkorea.or.kr/cms/resource/00/3312500_image2_1.jpg"
                    },                    
        7. 배열안의 배열에 각 인덱스에 응답해야할 패러글라이딩 체험장 데이터 예시 형식은 아래와 같아
                    {
                        "id": "51",
                        "tourismType:"PARAGLIDING_SPOT"
                        "name": "단양청춘패러",
                        "fullAddress": "충청북도 단양군 가곡면 두산길 262",
                        "longitude": 128.40072,
                        "latitude": 37.0022414,
                        "phoneNumber": "02-2088-4552",
                        "imgUrl": "",
                    },
                    
        다시 정리하자면 큰 객체 안에 배열의 배열로 반환해주면돼 바깥 배열의 인덱스는 여행 기간 중 날짜를 의미하고 (Day1,Day2), 안쪽 배열의 인덱스는 이동 경로를 의미하게하면돼.
        아래는 내가 제공할 데이터야 아래 데이터를 활용하면돼
        무조건 json객체로 반환해 너의 응답에대한 설명 필요없어 무조건 json만 반환해
        반환하는 배열의 이름은 schedules 로 반환해
        단, 내가 전달한 객체들 중에서 id필드가 Null이 아니라면 tourismType을 "PARAGLIDING_SPOT"으로 지정해서 응답해.
        그 외에  형식은 내가 제공한 형식을 지키면돼.
        관광지 데이터 목록
        %s
        패러글라이딩 체험장
        %s
        여행 시작 일
        %s
        여행 종료 일
        %s
        """;

    public ChatResponse createSchedule(final String paraglidingSpotJson,final String tourismSpotJson,final String scheduleStart,final String scheduleEnd) {
        final Prompt prompt = new Prompt(this.promptCommand.formatted(tourismSpotJson, paraglidingSpotJson,scheduleStart,scheduleEnd),
            OpenAiChatOptions.builder()
                .model(ChatModel.GPT_4_1_NANO)
                .responseFormat(
                    ResponseFormat.builder()
                        .type(ResponseFormat.Type.JSON_OBJECT)
                        .build()
                )
                .build());
        return this.openApiChatModel.call(prompt);
    }
}

