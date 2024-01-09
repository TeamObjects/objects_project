package com.objects.marketbridge.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseEntity {
    @Id
    @GeneratedValue
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
}