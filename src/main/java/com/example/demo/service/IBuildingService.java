package com.example.demo.service;

import java.util.List;

import org.springframework.retry.annotation.Retryable;

import com.example.demo.model.BuildingModel;
import com.example.demo.model.JwtToken;

public interface IBuildingService {

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    public List<BuildingModel> getBuildingAll(JwtToken jwtToken, String includedDeleted);

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    public BuildingModel getBuildingById(int id, JwtToken jwtToken, String includedDeleted);
    public BuildingModel createBuilding(BuildingModel building, JwtToken jwtToken);

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    public BuildingModel updateBuilding(int id, BuildingModel building, JwtToken jwtToken);

    @Retryable(retryFor = RuntimeException.class, maxAttempts = 5)
    public void deleteBuilding(int id, JwtToken jwtToken);
}