package com.choisong.flyon.trippost.service;

import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.trippost.dto.TripPostCommentRequest;
import com.choisong.flyon.trippost.dto.TripPostCommentResponse;
import com.choisong.flyon.trippost.entity.TripPost;
import com.choisong.flyon.trippost.entity.TripPostComment;
import com.choisong.flyon.trippost.exception.TripPostCommentNotFoundException;
import com.choisong.flyon.trippost.exception.TripPostNotFoundException;
import com.choisong.flyon.trippost.mapper.TripPostCommentMapper;
import com.choisong.flyon.trippost.repository.TripPostCommentRepository;
import com.choisong.flyon.trippost.repository.TripPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripPostCommentService {

    private final TripPostCommentRepository commentRepo;
    private final TripPostRepository postRepo;
    private final TripPostCommentMapper mapper;
    private final MemberService memberService;

    public TripPostCommentResponse create(Long postId, TripPostCommentRequest request, Long memberId) {
        TripPost post = postRepo.findById(postId)
            .orElseThrow(TripPostNotFoundException::tripNotFound);
        var member = memberService.getMemberById(memberId);
        TripPostComment entity = mapper.toEntity(request, post, member);
        return mapper.toResponse(commentRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public List<TripPostCommentResponse> getByPostId(Long postId) {
        return commentRepo.findByTripPostId(postId).stream()
            .map(mapper::toResponse)
            .toList();
    }

    public TripPostCommentResponse update(Long commentId, TripPostCommentRequest request, Long memberId) {
        TripPostComment comment = commentRepo.findById(commentId)
            .orElseThrow(TripPostCommentNotFoundException::notFound);
        comment.validateOwner(memberId);
        comment.update(request.content());
        return mapper.toResponse(comment);
    }

    public void delete(Long commentId, Long memberId) {
        TripPostComment comment = commentRepo.findById(commentId)
            .orElseThrow(TripPostCommentNotFoundException::notFound);
        comment.validateOwner(memberId);
        commentRepo.delete(comment);
    }
}