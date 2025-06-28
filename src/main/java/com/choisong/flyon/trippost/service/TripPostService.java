package com.choisong.flyon.trippost.service;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.exception.MemberNotFoundException;
import com.choisong.flyon.member.repository.MemberRepository;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import com.choisong.flyon.trippost.exception.TripPostNotFoundException;
import com.choisong.flyon.trippost.mapper.TripPostMapper;
import com.choisong.flyon.trippost.repository.TripPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripPostService {

    private final TripPostRepository repository;
    private final TripPostMapper mapper;
    private final MemberRepository memberRepository;

    public TripPostResponse create(TripPostRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::notFound);

        TripPost post = mapper.toEntity(request, member);
        TripPost saved = repository.save(post);
        return mapper.toResponse(saved);
    }

    public TripPostResponse getById(Long id) {
        TripPost found = repository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);
        return mapper.toResponse(found);
    }
}