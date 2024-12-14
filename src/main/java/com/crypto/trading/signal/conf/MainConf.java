package com.crypto.trading.signal.conf;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class MainConf implements Filter {

    @Value("${allow.origin}")
    private String allowOrigin;

    private final Set<String> allowed = Set.of(
            String.format("https://%s", allowOrigin),
            String.format("https://www.%s", allowOrigin),
            "https://localhost:5173"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");

        if (origin != null && allowed.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET");
            response.setHeader("Access-Control-Max-Age", "3600");
        }

        chain.doFilter(req, res);
    }
}
