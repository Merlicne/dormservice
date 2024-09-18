package com.example.demo.service;

import com.example.demo.model.AdminModel;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.JwtToken;

public interface IAuthenticationService {
    
    JwtToken login(LoginRequest admin);

    AdminModel register(AdminModel admin);

    



}
