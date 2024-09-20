package com.example.demo.middleware;

import java.io.IOException;
import java.util.Collections;


import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.demo.entity.Role;
import com.example.demo.exception.UnAuthorizedException;

import io.jsonwebtoken.Claims;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
               final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try{
            final String token = authHeader.substring(7);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                final String username = jwtService.extractUsername(token);
                final Role role = jwtService.extractRole(token);

                if (jwtService.isTokenExpired(token)) {
                    throw new UnAuthorizedException("Token expired");
                }
                if (username == null || role == null) {
                    throw new UnAuthorizedException("Invalid token");
                }
                
                Claims claims = jwtService.extractAllClaims(token);
                String tokenIssuer = claims.getIssuer();

                if (!jwtService.isTokenIssuerValid(tokenIssuer)) {
                    throw new UnAuthorizedException("Invalid token issuer");
                }

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(tokenIssuer);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,Collections.singleton(authority));
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);

            }
        }catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}