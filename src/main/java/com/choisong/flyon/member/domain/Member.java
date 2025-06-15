package com.choisong.flyon.member.domain;

import com.choisong.flyon.oauth.OauthProviderType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OauthProviderType oauthProviderType;
    private String oauth2Id;
    private String nickname;
    private String imgUrl;

    public void updateNicknameAndImgUrl(final String nickname, final String imgUrl) {
        this.nickname = nickname;
        this.imgUrl = imgUrl;
    }

}
