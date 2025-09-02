package com.choisong.flyon.tourism.service;

import com.choisong.flyon.tourism.dto.TourismResponse;
import com.choisong.flyon.tourism.dto.TourismSliceResult;
import com.choisong.flyon.tourism.exception.TourismApiException;
import com.choisong.flyon.tourism.repository.TourismRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourismService {

    private final TourismRepository tourismRepository;

    public TourismSliceResult findNearbySlice(double lat, double lon, int radius, int page, int size) {
        JsonNode root;
        try {
            int pageNo = page < 0 ? 1 : (page + 1); // 0-base → 1-base
            int numOfRows = size <= 0 ? 10 : size;

            log.debug("Calling Tourism API: lat={}, lon={}, radius={}, pageNo={}, numOfRows={}",
                    lat, lon, radius, pageNo, numOfRows);

            root = tourismRepository.fetchLocationBased(lat, lon, radius, pageNo, numOfRows);
            log.trace("Tourism API raw response: {}", root);
        } catch (Exception e) {
            log.error("Tourism API call failed", e);
            throw TourismApiException.callFailed();
        }
        if (root == null) {
            log.error("Tourism API returned null response");
            throw TourismApiException.callFailed();
        }

        try {
            JsonNode response = root.path("response");
            String resultCode = response.path("header").path("resultCode").asText();
            String resultMsg = response.path("header").path("resultMsg").asText();

            if (!"0000".equals(resultCode)) {
                log.error("Tourism API error: resultCode={}, resultMsg={}", resultCode, resultMsg);
                throw TourismApiException.badResponse();
            }

            JsonNode body = response.path("body");
            int totalCount = body.path("totalCount").asInt(0);
            int pageNo = body.path("pageNo").asInt(page + 1);
            int numOfRows = body.path("numOfRows").asInt(size);

            log.info("Tourism API parsed body: totalCount={}, pageNo={}, numOfRows={}",
                    totalCount, pageNo, numOfRows);

            List<TourismResponse> items = new ArrayList<>();
            JsonNode arr = body.path("items").path("item");
            if (arr.isArray()) {
                for (JsonNode n : arr) {
                    items.add(TourismResponse.builder()
                            .title(get(n, "title"))
                            .addr1(get(n, "addr1"))
                            .addr2(get(n, "addr2"))
                            .mapX(get(n, "mapx"))
                            .mapY(get(n, "mapy"))
                            .tel(get(n, "tel"))
                            .firstImage(get(n, "firstimage"))
                            .build());
                }
            }

            boolean hasNext = (pageNo * numOfRows) < totalCount;
            return new TourismSliceResult(items, hasNext);

        } catch (TourismApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse Tourism API response: {}", root, e);
            throw TourismApiException.badResponse();
        }
    }

    private static String get(JsonNode node, String field) {
        JsonNode v = node.get(field);
        return v == null || v.isNull() ? null : v.asText(null);
    }
}