package com.choisong.flyon.tourism.service;

import com.choisong.flyon.schedule.dto.TourismType;
import com.choisong.flyon.tourism.dto.TourismResponse;
import com.choisong.flyon.tourism.dto.TourismSliceResult;
import com.choisong.flyon.tourism.exception.TourismApiException;
import com.choisong.flyon.tourism.repository.TourismRepository;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourismService {

    private final TourismRepository tourismRepository;

    private static String get(JsonNode node, String field) {
        JsonNode v = node.get(field);
        return (v == null || v.isNull()) ? null : v.asText(null);
    }

    public TourismSliceResult findNearbySlice(double lat, double lon, int radius, int page, int size, String arrange) {
        return findNearbySlice(lat, lon, radius, page, size, arrange, null);
    }

    public TourismSliceResult findNearbySlice(double lat, double lon, int radius, int page, int size, String arrange, Integer contentTypeId) {
        JsonNode root;
        final int reqPageNo = page < 0 ? 1 : (page + 1); // 0-base → 1-base
        final int reqPageSize = size <= 0 ? 10 : size;
        final String arrangeParam = (arrange == null || arrange.isBlank()) ? "S" : arrange;

        try {
            log.debug("Calling Tourism API: lat={}, lon={}, radius={}, pageNo={}, numOfRows={}, arrange={}, contentTypeId={}",
                    lat, lon, radius, reqPageNo, reqPageSize, arrangeParam, contentTypeId);

            root = tourismRepository.fetchLocationBased(lat, lon, radius, reqPageNo, reqPageSize, arrangeParam, contentTypeId);
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

            List<TourismResponse> items = new ArrayList<>();
            JsonNode arrNode = body.path("items").path("item");
            if (arrNode.isArray()) {
                for (JsonNode n : arrNode) {
                    items.add(TourismResponse.builder()
                            .name(get(n, "title"))
                            .tourismType(contentTypeId==12 ? TourismType.ATTRACTION_SPOT : TourismType.RESTAURANT_SPOT)
                            .fullAddress(get(n, "addr1"))
                            .longitude(get(n, "mapx"))
                            .latitude(get(n, "mapy"))
                            .phoneNumber(get(n, "tel"))
                            .imgUrl(get(n, "firstimage"))
                            .build());
                }
            }

            boolean hasNext = ((long) reqPageNo * reqPageSize) < totalCount;

            log.info("Tourism parsed: totalCount={}, reqPageNo={}, reqPageSize={}, returnedItems={}",
                    totalCount, reqPageNo, reqPageSize, items.size());

            return new TourismSliceResult(items, hasNext, totalCount);

        } catch (TourismApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse Tourism API response: {}", root, e);
            throw TourismApiException.badResponse();
        }
    }
}