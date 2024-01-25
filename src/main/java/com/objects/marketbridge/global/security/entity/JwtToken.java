package com.objects.marketbridge.global.security.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("JwtToken")
public class JwtToken {
    @Id
    private final String email;

    @Indexed
    private final String token;

    @Builder
    public JwtToken(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
