package com.choisong.flyon.paraglidingspot.repository;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import java.util.List;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParaglidingSpotRepository extends JpaRepository<ParaglidingSpot, Long> {

    @Query("select ps from ParaglidingSpot ps where st_contains(st_buffer(:center,:radius),ps.point)")
    List<ParaglidingSpot> findByCenterAndRadius(@Param("center") Point center, @Param("radius") Integer radius);

    @Query("select ps  from ParaglidingSpot ps where ps.address.sido = :sido and ps.address.sigungu = :sigungu")
    List<ParaglidingSpot> findByAddress(String sido, String sigungu);

    @Query("select ps  from ParaglidingSpot ps where ps.address.sido = :sido")
    List<ParaglidingSpot> findByAddressSido(String sido);

    @Query("select ps  from ParaglidingSpot ps where ps.address.sigungu = :sigungu")
    List<ParaglidingSpot> findByAddressSiGunGu(String sigungu);
}