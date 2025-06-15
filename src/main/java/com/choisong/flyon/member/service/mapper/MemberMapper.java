package com.choisong.flyon.member.service.mapper;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.oauth.provider.OauthMember;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {

    Member toMember(final OauthMember oauth2Member);
}
