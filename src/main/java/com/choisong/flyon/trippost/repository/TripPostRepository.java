package com.choisong.flyon.trippost.repository;

import com.choisong.flyon.trippost.entity.TripPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPostRepository extends JpaRepository<TripPost, Long> {
}
