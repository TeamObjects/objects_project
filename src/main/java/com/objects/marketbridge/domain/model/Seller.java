package com.objects.marketbridge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    Long id;
    String name;
    String bizNo;
    String owner;
    String category;
    String detail;
    String address;
    String licenseNo;
    String email;
    String accountNo;

    @Builder
    private Seller(String name, String bizNo, String owner, String category, String detail, String address, String licenseNo, String email, String accountNo) {
        this.name = name;
        this.bizNo = bizNo;
        this.owner = owner;
        this.category = category;
        this.detail = detail;
        this.address = address;
        this.licenseNo = licenseNo;
        this.email = email;
        this.accountNo = accountNo;
    }
}
