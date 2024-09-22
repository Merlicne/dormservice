package com.example.demo.service;

import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;

import java.util.List;

import org.springframework.retry.annotation.Retryable;

public interface IDormService {
    
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    DormModel getLatestDorm(JwtToken jwtToken);

    // @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    // DormModel getDormById(String id,JwtToken jwtToken, String includedDeleted);

    DormModel createDorm(DormModel dorm,JwtToken jwtToken);

    // DormModel updateDorm(String id, DormModel dorm,JwtToken jwtToken);

    // void deleteDorm(String id,JwtToken jwtToken);

}
