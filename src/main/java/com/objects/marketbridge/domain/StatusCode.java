package com.objects.marketbridge.domain;

import com.objects.marketbridge.domain.order.entity.StatusCodeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatusCode extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "status_code_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusCodeType statusCodeType;

    private String code;

    private String name;

    @Builder
    private StatusCode(StatusCodeType statusCodeType, String code, String name) {
        this.statusCodeType = statusCodeType;
        this.code = code;
        this.name = name;
    }
}
