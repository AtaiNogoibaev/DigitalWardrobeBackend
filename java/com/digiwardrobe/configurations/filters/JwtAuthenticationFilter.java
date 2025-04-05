package com.digiwardrobe.configurations.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.digiwardrobe.configurations.CustomUserDetailsService;
import com.digiwardrobe.configurations.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final int BEARER_PREFIX_LENGTH = 7;

    public JwtAuthenticationFilter(final JwtUtil jwtUtil, final CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            final String token = header.substring(BEARER_PREFIX_LENGTH);
            LOGGER.debug("JwtAuthenticationFilter: Extracted token: {}", token);
            try {
                final Claims claims = jwtUtil.parseToken(token).getPayload();
                final String email = claims.getSubject();
                LOGGER.debug("JwtAuthenticationFilter: Extracted email: {}", email);

                final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                LOGGER.debug("JwtAuthenticationFilter: Loaded user details: {}", userDetails);

                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
                LOGGER.debug("JwtAuthenticationFilter: Authentication set for user: {}", email);
            } catch (final Exception e) {
                LOGGER.error("JwtAuthenticationFilter: Error processing token: {}", e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}