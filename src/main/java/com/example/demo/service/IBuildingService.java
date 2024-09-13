package com.example.demo.service;

import com.example.demo.entity.Building;

public interface IBuildingService {
    public Iterable<Building> getBuildingAll();
    public Building getBuildingById(int id);
    public Building createBuilding(Building building);
    public Building updateBuilding(int id, Building building);
    public void deleteBuilding(int id);
    // public Iterable<Building> getBuildingByRoomId(int id);
}