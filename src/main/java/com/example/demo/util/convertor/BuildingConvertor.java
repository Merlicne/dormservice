package com.example.demo.util.convertor;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Building;
import com.example.demo.model.BuildingModel;



public class BuildingConvertor {
    
    private BuildingConvertor() {
        throw new IllegalStateException("Utility class");
    }
    
    public static List<BuildingModel> convertToModel(List<Building> buildings) {
        List<BuildingModel> buildingModels = new ArrayList<>();
        for (Building building : buildings) {
            buildingModels.add(convertToModel(building));
        }
        return buildingModels;
    }

    public static List<Building> convertToEntity(List<BuildingModel> buildingModels) {
        List<Building> buildings = new ArrayList<>();
        for (BuildingModel buildingModel : buildingModels) {
            buildings.add(convertToEntity(buildingModel));
        }
        return buildings;
    }





    public static BuildingModel convertToModel(Building building) {
        return BuildingModel.builder()
                .buildingID(building.getBuildingID())
                .buildingName(building.getBuildingName())
                .waterPrice(building.getWaterPrice())
                .elecPrice(building.getElecPrice())
                .createdAt(building.getCreatedAt())
                .updatedAt(building.getUpdatedAt())
                .deletedAt(building.getDeletedAt())
                .build();
    }
    
    public static Building convertToEntity(BuildingModel buildingModel) {
        return Building.builder()
                .buildingID(buildingModel.getBuildingID())
                .buildingName(buildingModel.getBuildingName())
                .waterPrice(buildingModel.getWaterPrice())
                .elecPrice(buildingModel.getElecPrice())
                .createdAt(buildingModel.getCreatedAt())
                .updatedAt(buildingModel.getUpdatedAt())
                .deletedAt(buildingModel.getDeletedAt())
                .build();
    }
    
}
