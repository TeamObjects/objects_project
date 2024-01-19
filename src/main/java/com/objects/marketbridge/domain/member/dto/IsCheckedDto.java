package com.objects.marketbridge.domain.member.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsCheckedDto {
    private Boolean isCheckEmail;

    @Builder
    public IsCheckedDto(Boolean isCheckEmail) {
        this.isCheckEmail = isCheckEmail;
    }

    public Boolean checkEmail() {
        return isCheckEmail;
    }
}
