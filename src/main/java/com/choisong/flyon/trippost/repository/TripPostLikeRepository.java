package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPostLikeRepository extends JpaRepository<TripPostLike, Long> {

    boolean existsByTripPostIdAndMemberId(Long tripPostId, Long memberId);

    Optional<TripPostLike> findByTripPostIdAndMemberId(Long tripPostId, Long memberId);

    long countByTripPostId(Long tripPostId);
}
