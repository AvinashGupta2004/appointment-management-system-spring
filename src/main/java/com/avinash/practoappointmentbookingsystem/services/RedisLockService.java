package com.avinash.practoappointmentbookingsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final StringRedisTemplate redisTemplate;

    public boolean acquireLock(String key, String value, Duration duration) {
        Boolean lockSuccessfullyAcquired = redisTemplate
                .opsForValue()
                .setIfAbsent(key, value, duration);

        return Boolean.TRUE.equals(lockSuccessfullyAcquired);
    }

    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
