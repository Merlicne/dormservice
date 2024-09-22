package com.example.demo.service;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.AdminModel;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.JwtToken;

public interface IAuthenticationService {
    
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    JwtToken login(LoginRequest admin);

    AdminModel register(AdminModel admin);

    



}
