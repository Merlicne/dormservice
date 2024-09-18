package com.example.demo.util.convertor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String createdAt = null;
        String updatedAt = null;
        String deletedAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (building.getCreatedAt() != null) {
            createdAt = building.getCreatedAt().format(formatter);
        } 
        if (building.getUpdatedAt() != null) {
            updatedAt = building.getUpdatedAt().format(formatter);
        } 
        if (building.getDeletedAt() != null) {
            deletedAt = building.getDeletedAt().format(formatter);
        } 
        return BuildingModel.builder()
                .buildingID(building.getBuildingID())
                .buildingName(building.getBuildingName())
                .waterPrice(building.getWaterPrice())
                .elecPrice(building.getElecPrice())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
    
    public static Building convertToEntity(BuildingModel buildingModel) {
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        LocalDateTime deletedAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (buildingModel.getCreatedAt() != null) {
            createdAt = LocalDateTime.parse(buildingModel.getCreatedAt(), formatter);
        }
        if (buildingModel.getUpdatedAt() != null) {
            updatedAt = LocalDateTime.parse(buildingModel.getUpdatedAt(),   formatter);
        } 
        if (buildingModel.getDeletedAt() != null) {
            deletedAt = LocalDateTime.parse(buildingModel.getDeletedAt(), formatter);
        } 
        return Building.builder()
                .buildingID(buildingModel.getBuildingID())
                .buildingName(buildingModel.getBuildingName())
                .waterPrice(buildingModel.getWaterPrice())
                .elecPrice(buildingModel.getElecPrice())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
    
}
