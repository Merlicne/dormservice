package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Building;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.service.IBuildingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingService implements IBuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public Building createBuilding(Building building){
        return buildingRepository.save(building);
    }

    @Override
    public Building getBuildingById(int id){
        return buildingRepository.findById(id).get();
    }

    @Override
    public Iterable<Building> getBuildingAll(){
        return buildingRepository.findAll();
    }

    @Override
    public Building updateBuilding(int id, Building building){
        buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found!"));
        return buildingRepository.save(building);
    }

    @Override
    public void deleteBuilding(int id){
        // buildingRepository.deleteById(id);
        Building building = buildingRepository.findById(id).get();
        building.setDeleted(true);
    }
}
