package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildingModel {
    
    private int buildingID;
    private String buildingName;
    private double waterPrice;
    private double elecPrice;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    
}
