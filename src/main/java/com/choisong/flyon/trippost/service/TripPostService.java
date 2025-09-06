package com.choisong.flyon.trippost.service;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import com.choisong.flyon.trippost.entity.TripPostLike;
import com.choisong.flyon.trippost.exception.TripPostLikeNotFoundException;
import com.choisong.flyon.trippost.exception.TripPostNotFoundException;
import com.choisong.flyon.trippost.mapper.TripPostMapper;
import com.choisong.flyon.trippost.repository.TripPostLikeRepository;
import com.choisong.flyon.trippost.repository.TripPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripPostService {

    private final TripPostRepository tripPostRepository;
    private final TripPostMapper tripPostmapper;
    private final MemberService memberService;
    private final TripPostLikeRepository tripPostLikeRepository;

    public TripPostResponse create(TripPostRequest request, Long memberId) {
        Member member = memberService.getMemberById(memberId);
        TripPost entity = tripPostmapper.toEntity(request, member);
        return tripPostmapper.toResponse(tripPostRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public TripPostResponse getById(Long id) {
        TripPost post = tripPostRepository.findById(id)
            .orElseThrow(TripPostNotFoundException::tripNotFound);
        return tripPostmapper.toResponse(post);
    }

    @Transactional(readOnly = true)
    public List<TripPostResponse> getAll() {
        return tripPostRepository.findAll().stream()
            .map(tripPostmapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<TripPostResponse> getByMemberId(Long memberId) {
        return tripPostRepository.findByMemberId(memberId).stream()
            .map(tripPostmapper::toResponse)
            .toList();
    }

    public Slice<TripPostResponse> getSortedPosts(int page, int size, String sortType) {
        Sort sort = "like".equals(sortType)
            ? Sort.by(Sort.Direction.DESC, "likeCount")
            : Sort.by(Sort.Direction.DESC, "createdAt");

        Pageable pageable = PageRequest.of(page, size, sort);

        Slice<TripPost> result = "like".equals(sortType)
            ? tripPostRepository.findAllByOrderByLikeCountDesc(pageable)
            : tripPostRepository.findAllByOrderByCreatedAtDesc(pageable);

        return result.map(tripPostmapper::toResponse);
    }

    public TripPostResponse update(Long id, TripPostRequest request, Long memberId) {
        TripPost post = tripPostRepository.findById(id)
            .orElseThrow(TripPostNotFoundException::tripNotFound);

        post.validateOwner(memberId);

        post.update(request.title(), request.content());
        return tripPostmapper.toResponse(post);
    }

    public void delete(Long id, Long memberId) {
        TripPost post = tripPostRepository.findById(id)
            .orElseThrow(TripPostNotFoundException::tripNotFound);

        post.validateOwner(memberId);

        tripPostRepository.delete(post);
    }

    public boolean toggleLike(Long postId, Long memberId) { // 토글 방식으로 구현했습니다.
        TripPost post = tripPostRepository.findById(postId)
            .orElseThrow(TripPostNotFoundException::tripNotFound);

        boolean alreadyLiked = tripPostLikeRepository.existsByTripPostIdAndMemberId(postId, memberId);

        if (alreadyLiked) {
            TripPostLike like = tripPostLikeRepository.findByTripPostIdAndMemberId(postId, memberId)
                .orElseThrow(TripPostLikeNotFoundException::likeNotFound);
            tripPostLikeRepository.delete(like);
            post.decreaseLike();
            return false; // 좋아요 취소
        } else {
            Member member = memberService.getMemberById(memberId);
            TripPostLike like = TripPostLike.builder()
                .tripPost(post)
                .member(member)
                .build();
            tripPostLikeRepository.save(like);
            post.increaseLike();
            return true; // 좋아요 등록
        }
    }
}