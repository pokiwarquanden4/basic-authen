package com.example.basicauthen.service.ratelimit;

import io.github.bucket4j.Bucket;

public interface IRateLimitService {
    Bucket newBucket(Long userId);

    Bucket resolveBucket(Long userId);
}
