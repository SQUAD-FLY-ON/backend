package com.choisong.flyon.paraglidingspot.repository;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.locationtech.jts.geom.Point;

@Repository
public interface ParaglidingSpotRepository extends JpaRepository<ParaglidingSpot,Long> {

    @Query("select ps from ParaglidingSpot ps where st_contains(st_buffer(:center,:radius),ps.point)")
    List<ParaglidingSpot> findByCenterAndRadius(@Param("center") Point center, @Param("radius") Integer radius);

}