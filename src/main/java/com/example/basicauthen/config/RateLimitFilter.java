package com.example.basicauthen.config;

import com.example.basicauthen.model.User;
import com.example.basicauthen.repository.IUserRepository;
import com.example.basicauthen.service.ratelimit.IRateLimitService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class RateLimitFilter extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRateLimitService rateLimitingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            User loggedInUser = userRepository.findByUsername(authentication.getName()).get();
            final Bucket tokenBucket = rateLimitingService.resolveBucket(loggedInUser.getId());
            final ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

            if (!probe.isConsumed()) {
                long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
                response.addHeader("Retry After Seconds", String.valueOf(waitForRefill));
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                        "Request limit linked to your current plan has been exhausted");
            }
        }
        filterChain.doFilter(request, response);
    }
}
