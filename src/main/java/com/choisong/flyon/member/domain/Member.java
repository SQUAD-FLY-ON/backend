package com.choisong.flyon.member.domain;

import com.choisong.flyon.oauth.provider.OauthProviderType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OauthProviderType oauthProviderType;
    private String oauth2Id;
    private String nickname;
    private String loginId;
    private String encodedPassword;
    private String imgUrl;

    @Builder
    public Member(final OauthProviderType oauthProviderType, final String oauth2Id, final String nickname,
        final String loginId,
        final String encodedPassword, final String imgUrl) {
        this.oauthProviderType = oauthProviderType;
        this.oauth2Id = oauth2Id;
        this.nickname = nickname;
        this.loginId = loginId;
        this.encodedPassword = encodedPassword;
        this.imgUrl = imgUrl;
    }

    public void updateNicknameAndImgUrl(final String nickname, final String imgUrl) {
        this.nickname = nickname;
        this.imgUrl = imgUrl;
    }
}
