package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripPostCommentRepository extends JpaRepository<TripPostComment, Long> {
    List<TripPostComment> findByTripPostId(Long tripPostId);
}
