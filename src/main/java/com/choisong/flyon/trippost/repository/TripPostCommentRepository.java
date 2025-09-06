package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPostComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPostCommentRepository extends JpaRepository<TripPostComment, Long> {

    List<TripPostComment> findByTripPostId(Long tripPostId);
}
