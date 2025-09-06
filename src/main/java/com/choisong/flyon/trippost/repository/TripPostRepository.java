package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPost;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPostRepository extends JpaRepository<TripPost, Long> {

    List<TripPost> findByMemberId(Long memberId);

    Slice<TripPost> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Slice<TripPost> findAllByOrderByLikeCountDesc(Pageable pageable);
}
