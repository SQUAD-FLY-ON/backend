package com.choisong.flyon.trippost.controller;

import com.choisong.flyon.security.annotation.AuthenticationMember;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.service.TripPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip-posts")
@Tag(name = "TripPost", description = "여행 게시글 관련 API")
public class TripPostController {

    private final TripPostService tripPostService;

    /**
     * 게시글 생성
     */
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @PostMapping
    public TripPostResponse create(@RequestBody TripPostRequest request,
        @AuthenticationMember Long memberId) {
        return tripPostService.create(request, memberId);
    }

    /**
     * 게시글 단건 조회
     */
    @Operation(summary = "게시글 단건 조회", description = "ID로 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public TripPostResponse getById(@PathVariable Long id) {
        return tripPostService.getById(id);
    }

    /**
     * 전체 게시글 목록 조회
     */
    @Operation(summary = "전체 게시글 목록 조회", description = "모든 게시글을 조회합니다.")
    @GetMapping
    public List<TripPostResponse> getAll() {
        return tripPostService.getAll();
    }

    @GetMapping("/sorted")
    @Operation(summary = "정렬된 게시글 목록 조회", description = "정렬 방식에 따라 게시글 목록을 무한스크롤 방식으로 조회합니다.")
    public Slice<TripPostResponse> getSortedPosts(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(defaultValue = "latest") String sortType
    ) {
        return tripPostService.getSortedPosts(page, size, sortType);
    }

    /**
     * 로그인한 사용자의 게시글 목록 조회
     */
    @Operation(summary = "내 게시글 목록 조회", description = "로그인한 사용자의 게시글 목록을 조회합니다.")
    @GetMapping("/me")
    public List<TripPostResponse> getMyPosts(@AuthenticationMember Long memberId) {
        return tripPostService.getByMemberId(memberId);
    }

    /**
     * 게시글 수정
     */
    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PutMapping("/{id}")
    public TripPostResponse update(@PathVariable Long id,
        @RequestBody TripPostRequest request,
        @AuthenticationMember Long memberId) {
        return tripPostService.update(id, request, memberId);
    }

    /**
     * 게시글 삭제
     */
    @Operation(summary = "게시글 삭제", description = "기존 게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
        @AuthenticationMember Long memberId) {
        tripPostService.delete(id, memberId);
    }

    @Operation(summary = "게시글 좋아요 토글", description = "좋아요 누르기/취소하기")
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Map<String, String>> toggleLike(@PathVariable Long postId,
        @AuthenticationMember Long memberId) {
        boolean isLiked = tripPostService.toggleLike(postId, memberId);
        Map<String, String> response = Map.of("message",
            isLiked ? "좋아요가 등록되었습니다." : "좋아요가 취소되었습니다.");
        return ResponseEntity.ok(response);
    }
}