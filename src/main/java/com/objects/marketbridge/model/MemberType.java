package com.objects.marketbridge.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {
    CUSTOMER("고객"),
    SELLER("판매자"),
    ADMIN("관리자");

    private final String text;
}
