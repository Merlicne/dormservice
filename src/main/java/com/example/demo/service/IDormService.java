package com.example.demo.service;

import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;

import java.util.List;

import org.springframework.retry.annotation.Retryable;

public interface IDormService {
    
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    DormModel getLatestDorm(JwtToken jwtToken);
    List<DormModel> getUpdatedHistory(JwtToken jwtToken);
    DormModel createDorm(DormModel dorm,JwtToken jwtToken);


}
