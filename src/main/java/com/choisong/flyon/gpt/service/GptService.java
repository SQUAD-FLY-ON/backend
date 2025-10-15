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
내가 주는 데이터는 관광지 목록, 음식점 목록, 패러글라이딩 체험장, 여행 시작일과 종료일이다. 
너는 위도/경도를 활용하여 일별 최적의 여행 경로를 만들어야 한다. 

### 반드시 지켜야 할 조건
1. 패러글라이딩 체험장은 전체 여행 일정 중 하루만 포함해야 한다. 
   - 가급적 여행 초반 일정에 배치한다. (필수는 아님, 날짜와 위치를 고려)
2. 내가 제공한 관광지/음식점 데이터는 중복 없이 전부 일정에 포함해야 한다. 
3. 여행 기간(`scheduleStart`, `scheduleEnd`)을 기준으로 일수를 계산한다.
   - 일수 = 종료일 포함 계산 (`종료일 - 시작일 + 1`)
   - `schedules` 배열의 길이는 반드시 **이 일수와 동일해야 한다.**
   - 예: 시작=9/1, 종료=9/3 → 총 3일 → `schedules` 길이=3
   - 일정이 없는 날도 **빈 배열([])** 로 유지해야 한다. (삭제 절대 금지)
4. 각 날짜의 내부 배열은 이동 경로 순서를 나타낸다.
5. 관광지와 음식점이 날짜별로 고르게 분배되도록 구성한다.
6. 결과는 오직 **JSON 객체만 반환**해야 한다. (설명, 주석 금지)
7. 최상위 구조는 다음과 같다:
   {
     "schedules": [
       [ Day1 일정 ],
       [ Day2 일정 ],
       ...
     ]
   }

### 일정 데이터 형식
- 관광지:
  {
    "id": null,
    "tourismType": "ATTRACTION_SPOT",
    "name": "...",
    "fullAddress": "...",
    "longitude": "...",
    "latitude": "...",
    "phoneNumber": "...",
    "imgUrl": "..."
  }

- 음식점:
  {
    "id": null,
    "tourismType": "RESTAURANT_SPOT",
    "name": "...",
    "fullAddress": "...",
    "longitude": "...",
    "latitude": "...",
    "phoneNumber": "...",
    "imgUrl": "..."
  }

- 패러글라이딩:
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

※ id가 null이 아닐 경우 tourismType은 반드시 "PARAGLIDING_SPOT"이어야 함.

### 출력 예시
{
  "schedules": [
    [ ...Day1... ],
    [ ...Day2... ],
    [ ...Day3... ]
  ]
}

---

관광지/음식점 데이터 목록:
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
                .model(ChatModel.GPT_4_1) // 최신 모델로 교체 가능
                .responseFormat(
                    ResponseFormat.builder()
                        .type(ResponseFormat.Type.JSON_OBJECT)
                        .build()
                )
                .build());
        return this.openApiChatModel.call(prompt);
    }
}
