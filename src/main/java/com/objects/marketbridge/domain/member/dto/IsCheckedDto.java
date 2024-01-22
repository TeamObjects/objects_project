package com.objects.marketbridge.domain.member.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsCheckedDto {
    private Boolean isCheckEmail;

    @Builder
    public IsCheckedDto(boolean isCheckEmail) {

        this.isCheckEmail = isCheckEmail;
    }

    public Boolean getIsCheckEmail() {
        return isCheckEmail;
    }
}
