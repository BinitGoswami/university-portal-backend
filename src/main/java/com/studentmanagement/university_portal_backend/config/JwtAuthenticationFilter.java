package com.studentmanagement.university_portal_backend.config;

import com.studentmanagement.university_portal_backend.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Check if the header contains a Bearer token. If not, pass to the next
        // filter.
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract token (skip the first 7 characters: "Bearer ")
        jwt = authHeader.substring(7);
        username = jwtUtils.extractUsername(jwt);

        // 3. If we found a username and the user is not already authenticated in this
        // session
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetch the user from PostgreSQL
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. If the token is valid, officially authenticate them with Spring Security
            if (jwtUtils.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Update the Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5. Continue processing the request
        filterChain.doFilter(request, response);
    }
}
