package com.choisong.flyon.trippost.mapper;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.trippost.dto.TripPostCommentRequest;
import com.choisong.flyon.trippost.dto.TripPostCommentResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import com.choisong.flyon.trippost.entity.TripPostComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripPostCommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tripPost", source = "tripPost")
    @Mapping(target = "member", source = "member")
    @Mapping(target = "content", source = "request.content")
    TripPostComment toEntity(TripPostCommentRequest request, TripPost tripPost, Member member);

    @Mapping(target = "memberId", source = "member.id")
    TripPostCommentResponse toResponse(TripPostComment entity);
}
