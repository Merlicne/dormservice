package com.example.demo.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Building;
import com.example.demo.entity.Role;
import com.example.demo.exception.NotFoundException;
import com.example.demo.logs.Logger;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.BuildingModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.service.IBuildingService;
import com.example.demo.util.convertor.BuildingConvertor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingService implements IBuildingService {
    
    private final BuildingRepository buildingRepository;

    private final JwtService jwtService;

    @Override
    public BuildingModel createBuilding(BuildingModel buildingModel, JwtToken jwtToken){
        Logger.info("Create building");

        jwtService.allowRoles(jwtToken, Role.ADMIN);


        Building building = BuildingConvertor.convertToEntity(buildingModel);
        building = buildingRepository.save(building);
        return BuildingConvertor.convertToModel(building);
    }

    @Override
    public BuildingModel getBuildingById(int id, JwtToken jwtToken){
        Logger.info("Get building by id");

        jwtService.allowRoles(jwtToken, Role.ADMIN, Role.TENANT);

        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
        return BuildingConvertor.convertToModel(building);
    }

    @Override
    public List<BuildingModel> getBuildingAll(JwtToken jwtToken){
        Logger.info("Get all building");

        jwtService.allowRoles(jwtToken, Role.ADMIN, Role.TENANT);

        List<BuildingModel> buildingModel = BuildingConvertor.convertToModel(buildingRepository.findAll());
        return buildingModel;
    }

    @Override
    public BuildingModel updateBuilding(int id, BuildingModel buildingModel, JwtToken jwtToken){
        Logger.info("Update building");

        jwtService.allowRoles(jwtToken, Role.ADMIN);
        
        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
        building = BuildingConvertor.convertToEntity(buildingModel);
        building = buildingRepository.save(building);
        return BuildingConvertor.convertToModel(building);
    }

    @Override
    public void deleteBuilding(int id, JwtToken jwtToken){
        Logger.info("Delete building");

        jwtService.allowRoles(jwtToken, Role.ADMIN);

        buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
        buildingRepository.deleteById(id);
    }
}
