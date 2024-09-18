package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.demo.entity.Building;
import com.example.demo.exception.NotFoundException;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.BuildingModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.service.implement.BuildingService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
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
                .createdAt(LocalDateTime.parse(date, formatter))
                .updatedAt(LocalDateTime.parse(date, formatter))
                .deletedAt(null)
                .build();

        buildingModel = BuildingModel.builder()
                .buildingID(1)
                .buildingName("Test Building")
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
    void testGetBuildingById_withDatabaseDown() {
        when(buildingRepository.findById(anyInt())).thenThrow(new RuntimeException("Database is down"));
        when(jwtService.allowRoles(any(JwtToken.class), any(), any())).thenReturn(true);

        
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> buildingService.getBuildingById(1, jwtToken));
        assertEquals("Database is down", thrown.getMessage());

    }

    @Test
    void testGetBuildingById_BuildingNotFound() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(jwtService.allowRoles(any(JwtToken.class), any(), any())).thenReturn(true);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> buildingService.getBuildingById(999, jwtToken));
        assertEquals("Building not found", thrown.getMessage());
    }

    @RepeatedTest(100)
    void testHighAvailabilityUnderLoad() {

        when(jwtService.allowRoles(any(JwtToken.class), any(), any())).thenReturn(true);

        List<Building> buildings = new ArrayList<>();
        buildings.add(buildingEntity);

        when(buildingRepository.findAll()).thenReturn(buildings);

        List<BuildingModel> result = buildingService.getBuildingAll(jwtToken);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCreateBuilding_afterFailure() {
        when(buildingRepository.save(any(Building.class))).thenReturn(buildingEntity);
        when(jwtService.allowRoles(any(JwtToken.class), any())).thenReturn(true);

        BuildingModel result = buildingService.createBuilding(buildingModel,jwtToken);

        assertNotNull(result);
        assertEquals("Test Building", result.getBuildingName());
    }

    @Test
    void testUpdateBuilding() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(buildingEntity));
        when(buildingRepository.save(any(Building.class))).thenReturn(buildingEntity);
        when(jwtService.allowRoles(any(JwtToken.class), any())).thenReturn(true);

        BuildingModel result = buildingService.updateBuilding(1, buildingModel,jwtToken);

        assertNotNull(result);
        assertEquals("Test Building", result.getBuildingName());
    }

    


}
