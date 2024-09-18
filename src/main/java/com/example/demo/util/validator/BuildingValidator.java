package com.example.demo.util.validator;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.BuildingModel;

public class BuildingValidator {

    private BuildingValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateName(BuildingModel building) {
        if (building.getBuildingName() == null || building.getBuildingName().isEmpty()) {
            throw new BadRequestException("Building name is required");
        }
    }

    public static void validateId(BuildingModel building) {
        if (building.getBuildingID() <= 0) {
            throw new BadRequestException("Building ID is required");
        }
    }

    public static void validateWaterPrice(BuildingModel building) {
        if (building.getWaterPrice() <= 0) {
            throw new BadRequestException("Water price is required");
        }
    }

    public static void validateElecPrice(BuildingModel building) {
        if (building.getElecPrice() <= 0) {
            throw new BadRequestException("Electricity price is required");
        }
    }

    

    public static void validate(BuildingModel building) {
        validateName(building);
        validateWaterPrice(building);
        validateElecPrice(building);
    }
    
}
