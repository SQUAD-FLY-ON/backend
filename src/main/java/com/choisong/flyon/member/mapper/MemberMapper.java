package com.choisong.flyon.member.mapper;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.dto.MemberInfoResponse;
import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.oauth.provider.OauthMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member toEntity(OauthMember oauth2Member);

    Member toEntity(MemberRegisterRequest memberRegisterRequest, String encodedPassword, String imgUrl);

    @Mapping(target = "gliderBadge", expression = "java(member.getGliderBadge().getDisplayName())")
    @Mapping(target = "badgeAltitude", expression = "java(member.getGliderBadge().getHeight())")
    MemberInfoResponse toMemberInfoResponse(Member member);
}
