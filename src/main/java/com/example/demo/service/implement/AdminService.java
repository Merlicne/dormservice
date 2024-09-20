package com.example.demo.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Role;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.logs.Logger;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.AdminModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.IAdminServcie;
import com.example.demo.util.convertor.AdminConvertor;
import com.example.demo.util.role_authorization.RoleValidation;
import com.example.demo.util.validator.RestParamValidator;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminServcie{
    
    private final AdminRepository adminRepository;
    private final JwtService jwtService;

    @Override
    public List<AdminModel> getAllAdmin(JwtToken jwtToken, String includedDeleted) {
        Logger.info("Get all admin");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        includedDeleted =RestParamValidator.validateIncludedDeleted(includedDeleted);

        List<Admin> adminModel;
        if(includedDeleted.equalsIgnoreCase("true")){
            adminModel = adminRepository.findAll();
        }else{
            adminModel = adminRepository.findNotDeletedAll();
        }
        return AdminConvertor.toModel(adminModel);
    }

    @Override
    public AdminModel getAdminByUsername(String username, JwtToken jwtToken, String includedDeleted) {
        Logger.info("Get admin by username");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        includedDeleted =RestParamValidator.validateIncludedDeleted(includedDeleted);

        Admin admin;
        if(includedDeleted.equalsIgnoreCase("true")){
            admin = adminRepository.findByUsername(username).orElseThrow(() -> 
                            new NotFoundException("Admin not found")
                            );
        }else{
            admin = adminRepository.findNotDeletedByUsername(username).orElseThrow(() -> 
                            new NotFoundException("Admin not found")
                            );
        }
        return AdminConvertor.toModel(admin);
    }

    @Override
    public AdminModel updateAdmin(String username, AdminModel admin, JwtToken jwtToken) {
        Logger.info("Update admin");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        String tokenUsername = jwtService.extractUsername(jwtToken.getToken());
        if (!username.equals(tokenUsername)) {
            throw new ForbiddenException("You are not allowed to update this admin");
        }

        Admin oldAdmin = adminRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Admin not found"));
        Admin adminEntity = AdminConvertor.toEntity(admin);
        adminEntity.setUsername(username);
        adminEntity.setCreatedAt(oldAdmin.getCreatedAt());
        adminEntity.setDeletedAt(oldAdmin.getDeletedAt());
        adminRepository.save(adminEntity);
        return AdminConvertor.toModel(adminEntity);
    }

    @Override
    public void deleteAdmin(String username, JwtToken jwtToken) {
        Logger.info("Delete admin");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);
        String tokenUsername = jwtService.extractUsername(jwtToken.getToken());
        if (!username.equals(tokenUsername)) {
            throw new ForbiddenException("You are not allowed to delete this admin");
        }

        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Admin not found"));
        adminRepository.delete(admin);
    }




}