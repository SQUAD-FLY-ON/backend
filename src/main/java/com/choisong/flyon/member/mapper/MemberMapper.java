package com.choisong.flyon.member.mapper;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.oauth.provider.OauthMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member from(OauthMember oauth2Member);

    Member from(MemberRegisterRequest memberRegisterRequest,String encodedPassword);
}
