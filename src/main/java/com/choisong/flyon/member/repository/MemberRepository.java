package com.choisong.flyon.member.repository;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.oauth.provider.OauthProviderType;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(
        "select m from Member m where m.oauthProviderType =:provider and "
            + "m.oauth2Id =:id")
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Member> findByOauth2ProviderAndOauth2Id(OauthProviderType provider, String id);

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByLoginId(String loginId);
}
