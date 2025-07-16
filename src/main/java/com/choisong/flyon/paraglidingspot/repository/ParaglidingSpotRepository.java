package com.choisong.flyon.paraglidingspot.repository;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParaglidingSpotRepository extends JpaRepository<ParaglidingSpot,Long> {

}