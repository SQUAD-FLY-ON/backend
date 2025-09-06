package com.choisong.flyon.member.repository;

import com.choisong.flyon.member.domain.Roles;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    List<Roles> findByMemberId(Long memberId);
}
