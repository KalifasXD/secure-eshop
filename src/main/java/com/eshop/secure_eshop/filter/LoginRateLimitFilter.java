package com.eshop.secure_eshop.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(15))))
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        if ("/login".equals(req.getRequestURI()) && "POST".equals(req.getMethod())) {
            String ip = req.getRemoteAddr();
            Bucket bucket = cache.computeIfAbsent(ip, k -> newBucket());

            if (!bucket.tryConsume(1)) {
                res.setStatus(429);
                res.getWriter().write("Too many login attempts. Try again in 15 minutes.");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
