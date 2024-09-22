package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Role;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.AdminModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.implement.AdminService;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;

    private AdminModel adminModel;

    private JwtToken jwtToken;

    @BeforeEach
    void setUp() {
        admin = Admin.builder()
                .username("admin")
                .password("admin")
                .name("admin")
                .phone("0987654321")
                .email("abc@abc.com")
                .build();

        adminModel = AdminModel.builder()
                .username("admin")
                .password("admin")
                .name("admin")
                .phone("0987654321")
                .email("abc@abc.com")
                .build();

        jwtToken = JwtToken.builder()
                .token("token")
                .build();
        
    }

    @Test
    void getAllAdmin() {
        when(adminRepository.findAll()).thenReturn(List.of(admin));
        when(adminRepository.findNotDeletedAll()).thenReturn(List.of(admin));
        when(jwtService.extractRole(jwtToken.getToken())).thenReturn(Role.ADMIN);

        List<AdminModel> result = adminService.getAllAdmin(jwtToken,"true");
        assertNotNull(result);
        List<AdminModel> result2 = adminService.getAllAdmin(jwtToken,"false");
        assertNotNull(result2);

        verify(adminRepository, times(1)).findAll();
        verify(adminRepository, times(1)).findNotDeletedAll();


    }
    
}
