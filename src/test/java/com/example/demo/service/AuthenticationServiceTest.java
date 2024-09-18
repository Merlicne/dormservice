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
public class AuthenticationServiceTest {

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
    private JwtToken jwtToken;

    @BeforeEach
    public void setUp() {
        // Set up test data
        admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("encodedPassword");

        adminModel = new AdminModel();
        adminModel.setUsername("admin");
        adminModel.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("Password1");

        jwtToken = JwtToken.builder()
                .token("token")
                .expiresIn(3600)
                .build();
    }


    @Test
    public void testLoginSuccess() {
        // Mock repository findByUsername
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        // Mock authenticationManager.authenticate
        when(authenticationManager.authenticate(any())).thenReturn(null);
        // Mock jwtService.generateToken
        when(jwtService.generateToken(any(),any(Admin.class))).thenReturn("token");

        JwtToken token = authenticationService.login(loginRequest);

        // Assertions
        assertNotNull(token);
        assertEquals("token", token.getToken());

        // Verify
        verify(adminRepository).findByUsername("admin");
    }

    @Test
    public void testLoginUserNotFound() {
        // Mock repository to return empty
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.empty());

        // Assertions
        assertThrows(NotFoundException.class, () -> {
            authenticationService.login(loginRequest);
        });

        // Verify
        verify(adminRepository).findByUsername("admin");
    }

    @Test
    public void testRegistrySuccess() {

        // Mock password encoder
        when(passwordEncoder.encode("Password123")).thenReturn("encodedPassword");
        // Mock repository save
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        adminModel.setPassword("Password123");

        AdminModel result = authenticationService.register(adminModel);

        // Assertions
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());

        // Verify
        verify(adminRepository).save(any(Admin.class));
        verify(passwordEncoder).encode("Password123");
    }

    @Test
    public void testRegistryPasswordValidationFails() {
        
        // Set up test data
        adminModel.setPassword("password");

        // Assertions
        assertThrows(BadRequestException.class, () -> {
            authenticationService.register(adminModel);
        });

        // Verify no interaction with repository or encoder
        verify(adminRepository, never()).save(any(Admin.class));
    }
}
