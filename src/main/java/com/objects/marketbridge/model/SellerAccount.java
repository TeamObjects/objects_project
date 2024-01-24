package com.objects.marketbridge.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_account_id")
    Long id;

    Long balance;
    Long incoming;
    Long outgoing;

    @Builder
    public SellerAccount(Long balance, Long incoming, Long outgoing) {
        this.balance = balance;
        this.incoming = incoming;
        this.outgoing = outgoing;
    }
}
