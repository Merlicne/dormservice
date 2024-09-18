package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.logs.Logger;
import com.example.demo.model.AdminModel;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.JwtToken;
import com.example.demo.model.ResponseBody;
import com.example.demo.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/auth")
public class AdminController {
    
    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseBody<JwtToken> login(@RequestBody LoginRequest admin) {
        Logger.info("Login request: " + admin.getUsername());
        JwtToken token = authenticationService.login(admin);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                token);
    }

    @PostMapping("/register")
    public ResponseBody<AdminModel> register(@RequestBody AdminModel admin) {
        Logger.info("Register request: " + admin.getUsername());
        AdminModel adminModel = authenticationService.register(admin);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                adminModel);
    }


}
