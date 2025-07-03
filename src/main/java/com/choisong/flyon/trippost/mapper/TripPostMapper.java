package com.choisong.flyon.trippost.mapper;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripPostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", source = "member")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TripPost toEntity(TripPostRequest request, Member member);

    TripPostResponse toResponse(TripPost tripPost);
}
