package com.objects.marketbridge.domain.member.dto;

import com.objects.marketbridge.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FindPointDto extends BaseEntity {

    private Long balance;
    private Long inPoint;
    private Long outPoint;
    private String pointType;
    private String comments;
    private LocalDateTime createdAt;

    @Builder
    public FindPointDto(Long balance, Long inPoint,String pointType, Long outPoint, String comments, LocalDateTime createdAt) {
        this.balance = balance;
        this.inPoint = inPoint;
        this.outPoint = outPoint;
        this.pointType =pointType;
        this.comments = comments;
        this.createdAt = super.getCreatedAt();
    }
}
