package com.example.basicauthen.service.ratelimit;

import com.example.basicauthen.config.CacheConfiguration;
import com.example.basicauthen.model.Plan;
import com.example.basicauthen.model.User;
import com.example.basicauthen.repository.IUserRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitServiceImpl implements IRateLimitService {
    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    @Autowired
    private IUserRepository userRepository;
    private static CacheConfiguration userCache = new CacheConfiguration();

    public Bucket resolveBucket(String username) {
        return bucketCache.computeIfAbsent(username, this::newBucket);
    }


    public Bucket newBucket(String username) {
        Plan plan = getByUsername(username).get().getPlan();
        if (plan == null) {
            return Bucket4j.builder()
                    .addLimit(Bandwidth.classic(1000, Refill.intervally(1000, Duration.ofMinutes(10))))
                    .build();
            }

        final Integer limitPerHour =Math.abs(plan.getLimitPerHour()) ;
        if(limitPerHour != null && limitPerHour != 0) {
            return Bucket4j.builder()
                    .addLimit(Bandwidth.classic(limitPerHour, Refill.intervally(limitPerHour, Duration.ofMinutes(10))))
                    .build();
        }
        else {
            return Bucket4j.builder()
                    .addLimit(Bandwidth.classic(1000, Refill.intervally(1000, Duration.ofMinutes(10))))
                    .build();
        }
    }

    public Optional<User> getByUsername(String username) {
        Object result = userCache.getCache("getByUserName", username);
        System.out.println(result);
        if (result != null && result instanceof Optional) {
            return (Optional<User>) result;
        }
        Optional<User> resultValue = userRepository.findByUsername(username);
        userCache.putCache("getByUserName", resultValue, username);
        return resultValue;
    }
}
