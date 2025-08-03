package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripPostLikeRepository extends JpaRepository<TripPostLike, Long> {
    boolean existsByTripPostIdAndMemberId(Long tripPostId, Long memberId);
    Optional<TripPostLike> findByTripPostIdAndMemberId(Long tripPostId, Long memberId);
    long countByTripPostId(Long tripPostId);
}
