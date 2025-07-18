package com.choisong.flyon.trippost.entity;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.trippost.exception.TripPostAccessDeniedException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TripPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String title;

    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 추후에 TripCourse 매핑

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validateOwner(Long requestMemberId) {
        if (!this.member.getId().equals(requestMemberId)) {
            throw TripPostAccessDeniedException.accessDenied();
        }
    }
}