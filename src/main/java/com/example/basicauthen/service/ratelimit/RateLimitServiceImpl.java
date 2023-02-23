package com.example.basicauthen.service.ratelimit;

import com.example.basicauthen.model.Plan;
import com.example.basicauthen.repository.IUserRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class RateLimitServiceImpl implements IRateLimitService {
    private final Map<Long, Bucket> bucketCache = new ConcurrentHashMap<>();
    @Autowired
    private IUserRepository userRepository;

    public Bucket resolveBucket(Long userId) {
        return bucketCache.computeIfAbsent(userId, this::newBucket);
    }


    public Bucket newBucket(Long userId) {
        Plan plan = userRepository.findById(userId).get().getPlan();
        final Integer limitPerHour = plan.getLimitPerHour();
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(limitPerHour, Refill.intervally(limitPerHour, Duration.ofMinutes(10))))
                .build();
    }
}
