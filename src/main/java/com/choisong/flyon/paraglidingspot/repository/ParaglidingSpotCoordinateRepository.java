package com.choisong.flyon.paraglidingspot.repository;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ParaglidingSpotCoordinateRepository {

    private static final String PARAGLIDING_SPOT_KEY = "paraglidingspot";
    private final RedisTemplate<String, Object> redisTemplate;

    public void flushAllCoordinates() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    public void addLocation(final double latitude, final double longitude, final Long paraglidingSpotId) {
        final Point point = new Point(longitude, latitude);
        redisTemplate.opsForGeo().add(PARAGLIDING_SPOT_KEY, point, paraglidingSpotId.toString());
    }

    public void removeLocation(final Long paraglidingSpotId) {
        redisTemplate.opsForZSet().remove(PARAGLIDING_SPOT_KEY, paraglidingSpotId.toString());
    }

    public List<Long> findNearestSpotsWithRadiusLimit(double latitude, double longitude, double maxDistanceKm,
        int limit) {
        final Point center = new Point(longitude, latitude);
        final GeoResults<GeoLocation<Object>> results = getResults(maxDistanceKm, limit, center);

        if (results == null || results.getContent().isEmpty()) {
            return Collections.emptyList();
        }

        return results.getContent().stream()
            .map(result -> Long.parseLong((String) result.getContent().getName()))
            .toList();
    }

    private GeoResults<GeoLocation<Object>> getResults(final double maxDistanceKm, final int limit,
        final Point center) {
        return redisTemplate.opsForGeo().search(
            PARAGLIDING_SPOT_KEY,
            GeoReference.fromCoordinate(center),
            new Distance(maxDistanceKm, DistanceUnit.KILOMETERS),
            RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
                .includeDistance()
                .sortAscending()
                .limit(limit)
        );
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParaglidingSpotLocation {

        private String paraglidingSpotId;
        private double longitude;
        private double latitude;
    }
}
