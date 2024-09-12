package com.example.demo.controller;

import com.example.demo.entity.Building;

public interface IBuildingController {
    public Iterable<Building> getBuildingAll ();
    public Building getBuildingById (int id);
    public Building createBuilding (Building building);
    public Building updateBuilding (int id, Building building);
    public void deleteBuilding (int id);
}
