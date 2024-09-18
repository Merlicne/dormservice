package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.AdminModel;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.implement.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.middleware.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private Admin admin;
    private AdminModel adminModel;
    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("encodedPassword");

        adminModel = new AdminModel();
        adminModel.setUsername("admin");
        adminModel.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("Password1");
    }


    @Test
    void testLoginSuccess() {
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(any(),any(Admin.class))).thenReturn("token");

        JwtToken token = authenticationService.login(loginRequest);

        
        assertNotNull(token);
        assertEquals("token", token.getToken());
        verify(adminRepository).findByUsername("admin");
    }

    @Test
    void testLoginUserNotFound() {
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            authenticationService.login(loginRequest);
        });

        verify(adminRepository).findByUsername("admin");
    }

    @Test
    void testRegistrySuccess() {

        when(passwordEncoder.encode("Password123")).thenReturn("encodedPassword");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        adminModel.setPassword("Password123");

        AdminModel result = authenticationService.register(adminModel);

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());

        verify(adminRepository).save(any(Admin.class));
        verify(passwordEncoder).encode("Password123");
    }

    @Test
    void testRegistryPasswordValidationFails() {
        
        adminModel.setPassword("password");
        assertThrows(BadRequestException.class, () -> {
            authenticationService.register(adminModel);
        });

        adminModel.setPassword("Password");
        assertThrows(BadRequestException.class, () -> {
            authenticationService.register(adminModel);
        });

        adminModel.setPassword("password123");
        assertThrows(BadRequestException.class, () -> {
            authenticationService.register(adminModel);
        });

        adminModel.setPassword("PASSWORD123");
        assertThrows(BadRequestException.class, () -> {
            authenticationService.register(adminModel);
        });

        verify(adminRepository, never()).save(any(Admin.class));
    }
}
