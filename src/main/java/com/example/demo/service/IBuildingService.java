package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BuildingModel;
import com.example.demo.model.JwtToken;

public interface IBuildingService {
    public List<BuildingModel> getBuildingAll(JwtToken jwtToken, String includedDeleted);
    public BuildingModel getBuildingById(int id, JwtToken jwtToken, String includedDeleted);
    public BuildingModel createBuilding(BuildingModel building, JwtToken jwtToken);
    public BuildingModel updateBuilding(int id, BuildingModel building, JwtToken jwtToken);
    public void deleteBuilding(int id, JwtToken jwtToken);
}