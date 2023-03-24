package com.example.basicauthen.service.ratelimit;

import com.example.basicauthen.model.User;
import io.github.bucket4j.Bucket;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IRateLimitService {
    Bucket newBucket(String username);

    Bucket resolveBucket(String username);

    Optional<User> getByUsername(String username);
}
