package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripPostRepository extends JpaRepository<TripPost, Long> {
    List<TripPost> findByMemberId(Long memberId);
    Slice<TripPost> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Slice<TripPost> findAllByOrderByLikeCountDesc(Pageable pageable);
}
