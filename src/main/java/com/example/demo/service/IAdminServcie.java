package com.example.demo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.AdminModel;
import com.example.demo.model.JwtToken;

public interface IAdminServcie {
    
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    List<AdminModel> getAllAdmin(JwtToken jwtToken, String includedDeleted);

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    AdminModel getAdminByUsername(String username,JwtToken jwtToken, String includedDeleted);

    @Retryable(retryFor = SQLException.class, maxAttempts = 3)
    AdminModel updateAdmin(String username, AdminModel admin,JwtToken jwtToken);

    @Retryable(retryFor = SQLException.class, maxAttempts = 3)
    void deleteAdmin(String username,JwtToken jwtToken);
}
