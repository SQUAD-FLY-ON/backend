package com.choisong.flyon.auth.mapper;

import com.choisong.flyon.auth.dto.MemberInfo;
import com.choisong.flyon.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "id", target = "memberId")
    MemberInfo from(Member member);
}
