package com.objects.marketbridge.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TidRedisStorage {

    private final RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> getValueOperations() {
        return redisTemplate.opsForValue();
    }

    public void save(Long memberId, String tid, long expirationTime) {
        getValueOperations().set(memberId+":"+tid, tid, expirationTime, TimeUnit.MILLISECONDS);
    }

    public void delete(Long memberId, String tid) {
        redisTemplate.delete(memberId+":"+tid);
    }
}
