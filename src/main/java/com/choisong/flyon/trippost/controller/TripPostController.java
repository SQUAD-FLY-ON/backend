package com.choisong.flyon.trippost.controller;

import com.choisong.flyon.security.principal.MemberPrincipal;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.service.TripPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip-posts")
public class TripPostController {

    private final TripPostService service;

    /**
     * 게시글 생성
     */
    @PostMapping
    public TripPostResponse create(@RequestBody TripPostRequest request,
                                   @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return service.create(request, memberId);
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{id}")
    public TripPostResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * 전체 게시글 목록 조회
     */
    @GetMapping
    public List<TripPostResponse> getAll() {
        return service.getAll();
    }

    /**
     * 로그인한 사용자의 게시글 목록 조회
     */
    @GetMapping("/me")
    public List<TripPostResponse> getMyPosts(@AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return service.getByMemberId(memberId);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    public TripPostResponse update(@PathVariable Long id,
                                   @RequestBody TripPostRequest request,
                                   @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return service.update(id, request, memberId);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal MemberPrincipal principal) {
        Long memberId = Long.parseLong(principal.getName());
        service.delete(id, memberId);
    }
}