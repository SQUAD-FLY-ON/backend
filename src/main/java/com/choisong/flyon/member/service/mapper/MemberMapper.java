package com.choisong.flyon.member.service.mapper;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.oauth.provider.OauthMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);
    Member toMember(final OauthMember oauth2Member);
}
