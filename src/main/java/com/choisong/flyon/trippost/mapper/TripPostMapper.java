package com.choisong.flyon.trippost.mapper;

import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripPostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberId", source = "memberId")
    TripPost toEntity(TripPostRequest request, Long memberId);

    TripPostResponse toResponse(TripPost tripPost);
}
