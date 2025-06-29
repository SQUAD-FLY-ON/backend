package com.choisong.flyon.trippost.service;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.exception.MemberNotFoundException;
import com.choisong.flyon.member.repository.MemberRepository;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import com.choisong.flyon.trippost.exception.TripPostAccessDeniedException;
import com.choisong.flyon.trippost.exception.TripPostNotFoundException;
import com.choisong.flyon.trippost.mapper.TripPostMapper;
import com.choisong.flyon.trippost.repository.TripPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripPostService {

    private final TripPostRepository triprepository;
    private final TripPostMapper mapper;
    private final MemberRepository memberRepository;

    public TripPostResponse create(TripPostRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::notFound);
        TripPost entity = mapper.toEntity(request, member);
        return mapper.toResponse(triprepository.save(entity));
    }

    @Transactional(readOnly = true)
    public TripPostResponse getById(Long id) {
        TripPost post = triprepository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);
        return mapper.toResponse(post);
    }

    @Transactional(readOnly = true)
    public List<TripPostResponse> getAll() {
        return triprepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TripPostResponse> getByMemberId(Long memberId) {
        return triprepository.findByMemberId(memberId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TripPostResponse update(Long id, TripPostRequest request, Long memberId) {
        TripPost post = triprepository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);

        if (!post.getMember().getId().equals(memberId)) {
            throw TripPostAccessDeniedException.accessDenied();
        }

        post.update(request.title(), request.content(), request.startDate(), request.endDate());
        return mapper.toResponse(post);
    }

    public void delete(Long id, Long memberId) {
        TripPost post = triprepository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);

        if (!post.getMember().getId().equals(memberId)) {
            throw TripPostAccessDeniedException.accessDenied();
        }

        triprepository.delete(post);
    }
}