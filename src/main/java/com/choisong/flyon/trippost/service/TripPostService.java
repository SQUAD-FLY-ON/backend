package com.choisong.flyon.trippost.service;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.entity.TripPost;
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
    private final TripPostMapper TripPostmapper;
    private final MemberService memberService;

    public TripPostResponse create(TripPostRequest request, Long memberId) {
        Member member = memberService.getMemberById(memberId);
        TripPost entity = TripPostmapper.toEntity(request, member);
        return TripPostmapper.toResponse(triprepository.save(entity));
    }

    @Transactional(readOnly = true)
    public TripPostResponse getById(Long id) {
        TripPost post = triprepository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);
        return TripPostmapper.toResponse(post);
    }

    @Transactional(readOnly = true)
    public List<TripPostResponse> getAll() {
        return triprepository.findAll().stream()
                .map(TripPostmapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TripPostResponse> getByMemberId(Long memberId) {
        return triprepository.findByMemberId(memberId).stream()
                .map(TripPostmapper::toResponse)
                .toList();
    }

    public TripPostResponse update(Long id, TripPostRequest request, Long memberId) {
        TripPost post = triprepository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);

        post.validateOwner(memberId);

        post.update(request.title(), request.content());
        return TripPostmapper.toResponse(post);
    }

    public void delete(Long id, Long memberId) {
        TripPost post = triprepository.findById(id)
                .orElseThrow(TripPostNotFoundException::tripNotFound);

        post.validateOwner(memberId);

        triprepository.delete(post);
    }
}