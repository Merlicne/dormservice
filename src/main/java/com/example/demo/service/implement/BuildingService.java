package com.example.demo.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.example.demo.util.role_authorization.RoleValidation;
import com.example.demo.util.validator.BuildingValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingService implements IBuildingService {
    
    private final BuildingRepository buildingRepository;

    private final JwtService jwtService;

    
    @Override
    public BuildingModel getBuildingById(int id, JwtToken jwtToken){
        Logger.info("Get building by id");
        
        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
        return BuildingConvertor.convertToModel(building);
    }

    @Override
    public List<BuildingModel> getBuildingAll(JwtToken jwtToken){
        Logger.info("Get all building");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);
        
        List<BuildingModel> buildingModel = BuildingConvertor.convertToModel(buildingRepository.findAll());
        return buildingModel;
    }
    
    @Override
    @Transactional
    public BuildingModel createBuilding(BuildingModel buildingModel, JwtToken jwtToken){
        Logger.info("Create building");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        BuildingValidator.validate(buildingModel);
        Building building = BuildingConvertor.convertToEntity(buildingModel);
        building = buildingRepository.save(building);
        return BuildingConvertor.convertToModel(building);
    }
    
    @Override
    @Transactional
    public BuildingModel updateBuilding(int id, BuildingModel buildingModel, JwtToken jwtToken){
        Logger.info("Update building");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        
        Building oldBuilding = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
        
        buildingModel.setBuildingID(id);
        BuildingValidator.validate(buildingModel);
        BuildingValidator.validateId(buildingModel);

        Building building = BuildingConvertor.convertToEntity(buildingModel);
        building.setCreatedAt(oldBuilding.getCreatedAt());
        building.setDeletedAt(oldBuilding.getDeletedAt());
        building = buildingRepository.save(building);
        return BuildingConvertor.convertToModel(building);
    }

    @Override
    @Transactional
    public void deleteBuilding(int id, JwtToken jwtToken){
        Logger.info("Delete building");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building not found"));
        buildingRepository.deleteById(id);
    }
}
