package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Dorm;
import com.example.demo.entity.Role;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.DormRepository;
import com.example.demo.service.implement.DormService;

@ExtendWith(MockitoExtension.class)
class DormServiceTest {

    @Mock
    private DormRepository dormRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private DormService dormService;

    private Dorm dormEntity;
    private DormModel dormModel;
    private JwtToken jwtToken;

    @BeforeEach
    void setUp() {

        String date = "01-01-2021 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        UUID uuid = UUID.randomUUID();

        jwtToken = JwtToken.builder()
                            .token("token")
                            .expiresIn(1000)
                            .build();

        dormEntity = Dorm.builder()
                .dormID(uuid)
                .name("Test Dorm")
                .telephone("0123456789")
                .address("Test Address")
                .createdAt(LocalDateTime.parse(date, formatter))
                .createrToken("token")
                .build();

        dormModel = DormModel.builder()
                    .dormID(uuid)
                    .name("Test Dorm")
                    .telephone("0123456789")
                    .address("Test Address")
                    .createdAt(LocalDateTime.parse(date, formatter))
                    .createrToken("token")
                    .build();
    }

    @Test
    void testGetLatestDorm() {
        when(dormRepository.findLatestDorm()).thenReturn(Optional.of(dormEntity));
        when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);

        DormModel dormModels = dormService.getLatestDorm(jwtToken);

        assertNotNull(dormModels);
        assertEquals(dormModel, dormModels);
    }

    // @Test
    // void testGetDormById() {
    //     when(dormRepository.findById(dormEntity.getDormID())).thenReturn(java.util.Optional.of(dormEntity));
    //     when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);

    //     DormModel dorm = dormService.getDormById(dormEntity.getDormID().toString(), jwtToken, "true");

    //     assertNotNull(dorm);
    //     assertEquals(this.dormModel, dorm);
    // }

    // @Test
    // void testGetDormByIdNotFound() {
    //     when(dormRepository.findById(dormEntity.getDormID())).thenReturn(java.util.Optional.empty());
    //     when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);

    //     assertThrows(NotFoundException.class, () -> {
    //         dormService.getDormById(dormEntity.getDormID().toString(), jwtToken, "true");
    //     });
    // }

    @Test
    void testCreateDorm() {
        when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);
        when(dormRepository.save(dormEntity)).thenReturn(dormEntity);

        DormModel dorm = dormService.createDorm(dormModel, jwtToken);

        assertNotNull(dorm);
        assertEquals(this.dormModel, dorm);
    }

    // @Test
    // void testUpdateDorm() {
    //     when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);
    //     when(dormRepository.findById(dormEntity.getDormID())).thenReturn(java.util.Optional.of(dormEntity));
    //     when(dormRepository.save(dormEntity)).thenReturn(dormEntity);

    //     DormModel dorm = dormService.updateDorm(dormEntity.getDormID().toString(), dormModel, jwtToken);

    //     assertNotNull(dorm);
    //     assertEquals(this.dormModel, dorm);
    //     verify(dormRepository).save(dormEntity);
    // }

    // @Test
    // void testUpdateDormNotFound() {
    //     when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);
    //     when(dormRepository.findById(dormEntity.getDormID())).thenReturn(java.util.Optional.empty());

    //     assertThrows(NotFoundException.class, () -> {
    //         dormService.updateDorm(dormModel.getDormID().toString(), dormModel, jwtToken);
    //     });
    // }

    // @Test
    // void testDeleteDorm() {
    //     when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);
    //     when(dormRepository.findById(dormEntity.getDormID())).thenReturn(java.util.Optional.of(dormEntity));

    //     dormService.deleteDorm(dormEntity.getDormID().toString(), jwtToken);

    //     verify(dormRepository).deleteById(dormEntity.getDormID());
    // }

    // @Test
    // void testDeleteDormNotFound() {
    //     when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);
    //     when(dormRepository.findById(dormEntity.getDormID())).thenReturn(java.util.Optional.empty());

    //     assertThrows(NotFoundException.class, () -> {
    //             dormService.deleteDorm(dormModel.getDormID().toString(), jwtToken);
    //     });

    //     verify(dormRepository, times(0)).deleteById(dormEntity.getDormID());
    // }

    @Test
    void testDormInvalidRole() {
        when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.TENANT);
        assertThrows(ForbiddenException.class, () -> {
            dormService.createDorm(dormModel, jwtToken);
        });

        verify(dormRepository, times(0)).deleteById(dormEntity.getDormID());
        verify(dormRepository, times(0)).save(dormEntity);
    }


    
}
