package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.entity.Building;
import com.example.demo.entity.Role;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.BuildingModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.service.implement.BuildingService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private BuildingService buildingService;

    private Building buildingEntity;
    private BuildingModel buildingModel;
    private JwtToken jwtToken;

    @BeforeEach
    void setUp() {

        String date = "01-01-2021 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        jwtToken = JwtToken.builder()
                            .token("token")
                            .expiresIn(1000)
                            .build();

        buildingEntity = Building.builder()
                .buildingID(1)
                .buildingName("Test Building")
                .waterPrice(10)
                .elecPrice(10)
                .createdAt(LocalDateTime.parse(date, formatter))
                .updatedAt(LocalDateTime.parse(date, formatter))
                .deletedAt(null)
                .build();

        buildingModel = BuildingModel.builder()
                .buildingID(1)
                .buildingName("Test Building")
                .waterPrice(10)
                .elecPrice(10)
                .createdAt(date)
                .updatedAt(date)
                .deletedAt(null)
                .build();
    }

    @AfterEach
    void tearDown() {
        buildingEntity = null;
        buildingModel = null;
        jwtToken = null;

    }

    @Test
    void testGetBuildingAll() {
        when(buildingRepository.findAll()).thenReturn(List.of(buildingEntity));
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        List<BuildingModel> result = buildingService.getBuildingAll(jwtToken);
        
        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    void testGetBuildingById() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(buildingEntity));
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        BuildingModel result = buildingService.getBuildingById(1, jwtToken);

        assertNotNull(result);
        assertEquals("Test Building", result.getBuildingName());
    }

    @Test
    void testCreateBuilding() {
        when(buildingRepository.save(any(Building.class))).thenReturn(buildingEntity);
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        BuildingModel result = buildingService.createBuilding(buildingModel,jwtToken);

        assertNotNull(result);
        assertEquals("Test Building", result.getBuildingName());
    }



    @Test
    void testGetBuildingById_BuildingNotFound() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> buildingService.getBuildingById(999, jwtToken));
        assertEquals("Building not found", thrown.getMessage());
    }


    @Test
    void testUpdateBuilding() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(buildingEntity));
        when(buildingRepository.save(any(Building.class))).thenReturn(buildingEntity);
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        BuildingModel result = buildingService.updateBuilding(1, buildingModel,jwtToken);

        assertNotNull(result);
        assertEquals("Test Building", result.getBuildingName());
    }

    @Test
    void testUpdateBuilding_BuildingNotFound() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        assertThrows(NotFoundException.class, () -> buildingService.updateBuilding(999, buildingModel,jwtToken));

        verify(buildingRepository, times(0)).save(any(Building.class));
    }

    @Test
    void testDeleteBuilding() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(buildingEntity));
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        buildingService.deleteBuilding(1,jwtToken);

        verify(buildingRepository).deleteById(1);

    }

    @Test
    void testDeleteBuilding_BuildingNotFound() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        assertThrows(NotFoundException.class, () -> buildingService.deleteBuilding(999,jwtToken));

        verify(buildingRepository, times(0)).deleteById(999);
    }

    @Test
    void testInvalidRole() {
        when(jwtService.extractRole(anyString())).thenReturn(Role.TENANT);

        assertThrows(ForbiddenException.class, () -> buildingService.deleteBuilding(1,jwtToken));
        assertThrows(ForbiddenException.class, () -> buildingService.updateBuilding(1, buildingModel,jwtToken));
        assertThrows(ForbiddenException.class, () -> buildingService.createBuilding(buildingModel, jwtToken));

        verify(buildingRepository, times(0)).deleteById(1);
        verify(buildingRepository, times(0)).save(any(Building.class));
    }

    @Test
    void testBuildingInvalid(){

        when(jwtService.extractRole(anyString())).thenReturn(Role.ADMIN);

        buildingModel.setBuildingName("");
        assertThrows(BadRequestException.class, () -> buildingService.createBuilding(buildingModel, jwtToken));
        buildingModel.setBuildingName("Test Building");
        buildingModel.setWaterPrice(-1);
        assertThrows(BadRequestException.class, () -> buildingService.createBuilding(buildingModel, jwtToken));
        buildingModel.setWaterPrice(10);
        buildingModel.setElecPrice(-1);
        assertThrows(BadRequestException.class, () -> buildingService.createBuilding(buildingModel, jwtToken));

        verify(buildingRepository, times(0)).save(any(Building.class));
    }


    


}
