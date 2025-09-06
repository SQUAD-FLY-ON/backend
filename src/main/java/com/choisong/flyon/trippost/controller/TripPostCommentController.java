package com.choisong.flyon.trippost.controller;

import com.choisong.flyon.security.annotation.AuthenticationMember;
import com.choisong.flyon.trippost.dto.TripPostCommentRequest;
import com.choisong.flyon.trippost.dto.TripPostCommentResponse;
import com.choisong.flyon.trippost.service.TripPostCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip-posts/{postId}/comments")
@Tag(name = "TripPostComment", description = "여행 게시글 댓글 API")
public class TripPostCommentController {

    private final TripPostCommentService service;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
    public TripPostCommentResponse create(@PathVariable Long postId,
        @RequestBody TripPostCommentRequest request,
        @AuthenticationMember Long memberId) {
        return service.create(postId, request, memberId);
    }

    @GetMapping
    @Operation(summary = "댓글 목록 조회", description = "게시글에 달린 댓글을 조회합니다.")
    public List<TripPostCommentResponse> getByPostId(@PathVariable Long postId) {
        return service.getByPostId(postId);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "자신의 댓글을 수정합니다.")
    public TripPostCommentResponse update(@PathVariable Long postId,
        @PathVariable Long commentId,
        @RequestBody TripPostCommentRequest request,
        @AuthenticationMember Long memberId) {
        return service.update(commentId, request, memberId);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "자신의 댓글을 삭제합니다.")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long postId,
        @PathVariable Long commentId,
        @AuthenticationMember Long memberId) {
        service.delete(commentId, memberId);
        Map<String, String> response = Map.of("message", "댓글이 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }
}