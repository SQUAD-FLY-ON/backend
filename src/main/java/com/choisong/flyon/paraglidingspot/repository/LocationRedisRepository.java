package com.choisong.flyon.paraglidingspot.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class LocationRedisRepository {

    private static final String PARAGLIDING_SPOT_KEY = "paraglidingspot";
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void addLocation(final double latitude, final double longitude, final Long paraglidingSpotId) {
        final Point point = new Point(longitude, latitude);
        redisTemplate.opsForGeo().add(PARAGLIDING_SPOT_KEY, point, paraglidingSpotId);
    }

    public void removeLocation(final Long paraglidingSpotId) {
        redisTemplate.opsForZSet().remove(PARAGLIDING_SPOT_KEY, paraglidingSpotId);
    }

    public List<ParaglidingSpotLocation> geoSearch(final String courseId, final String crewId, final String userId) {
        GeoReference reference = GeoReference.fromMember(userId);
        Distance radius = new Distance(30, DistanceUnit.METERS);
        RedisGeoCommands.GeoSearchCommandArgs args = RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
            .includeCoordinates();
        GeoResults<GeoLocation<String>> results = redisTemplate.opsForGeo().search(PARAGLIDING_SPOT_KEY, reference, radius, args);
        return results.getContent()
            .stream().map(rs -> new ParaglidingSpotLocation(rs.getContent().getName(), rs.getContent().getPoint().getX(),
                rs.getContent().getPoint().getY())).toList();
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
