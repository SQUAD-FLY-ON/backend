package com.choisong.flyon.trippost.controller;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.security.exception.TokenNotFoundException;
import com.choisong.flyon.security.principal.MemberPrincipal;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.service.TripPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip-posts")
@Tag(name = "TripPost", description = "여행 게시글 관련 API")
public class TripPostController {

    private final TripPostService service;

    /**
     * 게시글 생성
     */
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @PostMapping
    public TripPostResponse create(@RequestBody TripPostRequest request,
                                   @AuthenticationPrincipal MemberPrincipal principal) {
        if (principal == null) {
            throw TokenNotFoundException.accessTokenHeaderNotFound();
        }

        Long memberId = Long.parseLong(principal.getName());
        return service.create(request, memberId);
    }

    /**
     * 게시글 단건 조회
     */
    @Operation(summary = "게시글 단건 조회", description = "ID로 게시글을 조회합니다.")
    @GetMapping("/{id}")
    public TripPostResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * 전체 게시글 목록 조회
     */
    @Operation(summary = "전체 게시글 목록 조회", description = "모든 게시글을 조회합니다.")
    @GetMapping
    public List<TripPostResponse> getAll() {
        return service.getAll();
    }

    /**
     * 로그인한 사용자의 게시글 목록 조회
     */
    @Operation(summary = "내 게시글 목록 조회", description = "로그인한 사용자의 게시글 목록을 조회합니다.")
    @GetMapping("/me")
    public List<TripPostResponse> getMyPosts(@AuthenticationPrincipal MemberPrincipal principal) {
        if (principal == null) {
            throw TokenNotFoundException.accessTokenHeaderNotFound();
        }

        Long memberId = Long.parseLong(principal.getName());
        return service.getByMemberId(memberId);
    }

    /**
     * 게시글 수정
     */
    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PutMapping("/{id}")
    public TripPostResponse update(@PathVariable Long id,
                                   @RequestBody TripPostRequest request,
                                   @AuthenticationPrincipal MemberPrincipal principal) {

        if (principal == null) {
            throw TokenNotFoundException.accessTokenHeaderNotFound();
        }

        Long memberId = Long.parseLong(principal.getName());
        return service.update(id, request, memberId);
    }

    /**
     * 게시글 삭제
     */
    @Operation(summary = "게시글 삭제", description = "기존 게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal MemberPrincipal principal) {

        if (principal == null) {
            throw TokenNotFoundException.accessTokenHeaderNotFound();
        }

        Long memberId = Long.parseLong(principal.getName());
        service.delete(id, memberId);
    }
}