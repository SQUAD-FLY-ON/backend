package com.choisong.flyon.tourism.repository;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;


@Repository
@Slf4j
public class TourismRepository {

    private static final String TOUR_BASE = "https://apis.data.go.kr/B551011/KorService2";

    private final RestClient tourismRestClient;
    private final Jackson2ObjectMapperBuilder mapperBuilder;
    private final String apiKey;

    public TourismRepository(Jackson2ObjectMapperBuilder mapperBuilder,
                             @Value("${tourism.api.key}") String apiKey) {
        this.mapperBuilder = mapperBuilder;
        this.apiKey = apiKey;
        this.tourismRestClient = RestClient.builder()
                .baseUrl(TOUR_BASE)
                .build();
    }

    /**
     * serviceKey 값 마스킹 처리
     */
    private static String redactServiceKey(String uri) {
        String keyName = "serviceKey=";
        int idx = uri.indexOf(keyName);
        if (idx < 0) {
            return uri;
        }
        int start = idx + keyName.length();
        int end = uri.indexOf('&', start);
        if (end < 0) {
            end = uri.length();
        }
        String val = uri.substring(start, end);
        return uri.substring(0, start) + mask(val) + uri.substring(end);
    }

    /**
     * 마스킹 처리
     */
    private static String mask(String key) {
        if (key == null || key.length() <= 8) {
            return "****";
        }
        return key.substring(0, 4) + "****" + key.substring(key.length() - 4);
    }

    /**
     * XML 태그 값 추출
     */
    private static String findBetween(String s, String a, String b) {
        int i = s.indexOf(a);
        if (i < 0) {
            return null;
        }
        int j = s.indexOf(b, i + a.length());
        if (j < 0) {
            return null;
        }
        return s.substring(i + a.length(), j).trim();
    }

    /**
     * 공공데이터 LocationBased 조회 (pageNo는 1-base)
     */
    public JsonNode fetchLocationBased(double lat, double lon, int radius, int pageNo, int numOfRows, String arrange) {
        return fetchLocationBased(lat, lon, radius, pageNo, numOfRows, arrange, null);
    }

    /**
     * 공공데이터 LocationBased 조회 (pageNo는 1-base) + contentTypeId 선택적 필터
     */
    public JsonNode fetchLocationBased(double lat, double lon, int radius, int pageNo, int numOfRows, String arrange, Integer contentTypeId) {

        String encodedKey = URLEncoder.encode(apiKey.trim(), StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TOUR_BASE + "/locationBasedList2")
                .queryParam("MobileOS", "WEB")
                .queryParam("MobileApp", "flyon")
                .queryParam("_type", "json")
                .queryParam("mapX", lon)
                .queryParam("mapY", lat)
                .queryParam("radius", radius)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("arrange",
                        arrange); // (A=제목순,C=수정일순, D=생성일순, E=거리순) 대표이미지가 반드시 있는 정렬 (O=제목순, Q=대표이미지 수정일순, R=대표이미지 생성일순,S=대표이미지 거리순). 기본값은 S

        if (contentTypeId != null) {
            builder.queryParam("contentTypeId", contentTypeId);
        }

        String baseQs = builder
                .build(false)
                .toUriString();

        String fullUrl = baseQs + "&serviceKey=" + encodedKey;

        // 요청 URI 로깅
        log.info("[TourismAPI] Request URI (full): {}", fullUrl);
        log.debug("[TourismAPI] Request URI (masked): {}", redactServiceKey(fullUrl));

        ResponseEntity<String> resp = tourismRestClient.get()
                .uri(URI.create(fullUrl))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        String body = resp.getBody();
        if (body == null) {
            log.error("[TourismAPI] Empty response");
            throw new RuntimeException("Empty response from Tourism API");
        }
        if (body.startsWith("<")) {
            // XML 에러 응답 처리
            String errMsg = findBetween(body, "<errMsg>", "</errMsg>");
            String authMsg = findBetween(body, "<returnAuthMsg>", "</returnAuthMsg>");
            String reason = findBetween(body, "<returnReasonCode>", "</returnReasonCode>");
            log.error("[TourismAPI] XML error. code={}, authMsg={}, errMsg={}", reason, authMsg, errMsg);
            throw new RuntimeException("Tourism API error: " + (authMsg != null ? authMsg : errMsg));
        }

        try {
            return mapperBuilder.build().readTree(body);
        } catch (Exception e) {
            log.error("[TourismAPI] JSON parse error. body(head 500)={}",
                    body.substring(0, Math.min(500, body.length())), e);
            throw new RuntimeException("Failed to parse tourism API response", e);
        }
    }
}