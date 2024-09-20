package com.example.demo.service.implement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.logs.Logger;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.AdminModel;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.JwtToken;
import com.example.demo.util.convertor.AdminConvertor;
import com.example.demo.util.validator.PasswordValidator;
import com.example.demo.entity.Role;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final JwtService jwtService;

    private final AdminRepository adminRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public JwtToken login(LoginRequest input) {
        Logger.info("Login request for user: " + input.getUsername());
        Admin admin = adminRepository.findByUsername(input.getUsername())
                                    .orElseThrow(() -> new NotFoundException("User not found"));
        
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", admin.getRole());

        String token = jwtService.generateToken(claims,admin);


        return JwtToken.builder()
                .token(token)
                .expiresIn(jwtService.getExpirationTime())
                .build();
}

    public AdminModel register(AdminModel input) {
        Logger.info("Register request for user: " + input.getUsername());

        Admin adminExist = adminRepository.findByUsername(input.getUsername()).orElse(null);
        if(adminExist != null){
            throw new BadRequestException("Username already exists");
        }

        PasswordValidator.validate(input.getPassword());
        input.setPassword(passwordEncoder.encode(input.getPassword()));
        Admin admin = AdminConvertor.toEntity(input);
        admin.setRole(Role.ADMIN);
        admin = adminRepository.save(admin);
        return AdminConvertor.toModel(admin);
    }

    


}
