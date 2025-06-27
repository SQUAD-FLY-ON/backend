package com.choisong.flyon.trippost.controller;

import com.choisong.flyon.security.principal.MemberPrincipal;
import com.choisong.flyon.trippost.dto.TripPostRequest;
import com.choisong.flyon.trippost.dto.TripPostResponse;
import com.choisong.flyon.trippost.service.TripPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip-posts")
public class TripPostController {

    private final TripPostService service;

    @PostMapping
    public TripPostResponse create(@RequestBody TripPostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        Long memberId = Long.parseLong(principal.getName());
        return service.create(request, memberId);
    }

    @GetMapping("/{id}")
    public TripPostResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
}