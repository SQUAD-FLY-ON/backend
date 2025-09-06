package com.choisong.flyon.jwt.repository;

import com.choisong.flyon.jwt.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteByMemberId(String memberId);
}
