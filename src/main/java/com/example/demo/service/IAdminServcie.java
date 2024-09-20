package com.example.demo.service;

import java.util.List;

import com.example.demo.model.AdminModel;
import com.example.demo.model.JwtToken;

public interface IAdminServcie {
    
    List<AdminModel> getAllAdmin(JwtToken jwtToken, String includedDeleted);

    AdminModel getAdminByUsername(String username,JwtToken jwtToken, String includedDeleted);

    AdminModel updateAdmin(String username, AdminModel admin,JwtToken jwtToken);

    void deleteAdmin(String username,JwtToken jwtToken);
}
