package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.logs.Logger;
import com.example.demo.model.AdminModel;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.JwtToken;
import com.example.demo.model.ResponseBody;
import com.example.demo.service.IAdminServcie;
import com.example.demo.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
    
    private final IAuthenticationService authenticationService;
    private final IAdminServcie adminService;

    @PostMapping("/admin/auth/login")
    public ResponseBody<JwtToken> login(
                                    @RequestBody LoginRequest admin
                                    ) {
        Logger.info("Login request: " + admin.getUsername());
        JwtToken token = authenticationService.login(admin);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                token);
    }

    @PostMapping("/admin/auth/register")
    public ResponseBody<AdminModel> register(
                                        @RequestBody AdminModel admin
                                        ) {
        Logger.info("Register request: " + admin.getUsername());
        AdminModel adminModel = authenticationService.register(admin);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                adminModel);
    }

    @GetMapping("/admin")
    public ResponseBody<List<AdminModel>> getAlladmin(
                                                @RequestHeader("Authorization") String token, 
                                                @RequestParam(required = false, name = "includedDeleted") String includedDeleted
                                                ) {
        Logger.info("Get admin by username");
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        List<AdminModel> adminModel = adminService.getAllAdmin(jwtToken, includedDeleted);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                adminModel);
    }

    @GetMapping("/admin/{username}")
    public ResponseBody<AdminModel> getAdminByUsername(
                                                @RequestHeader("Authorization") String token,
                                                @PathVariable String username,
                                                @RequestParam(required = false, name = "includedDeleted") String includedDeleted
                                                ) {
        Logger.info("Get admin by username");
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        AdminModel adminModel = adminService.getAdminByUsername(username, jwtToken, includedDeleted);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                adminModel);
    }

    @PutMapping("/admin/{username}")
    public ResponseBody<AdminModel> updateAdmin(
                                            @RequestHeader("Authorization") String token,
                                            @PathVariable String username, 
                                            @RequestBody AdminModel admin
                                            ) {
        Logger.info("Update admin");
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        AdminModel adminModel = adminService.updateAdmin(username, admin, jwtToken);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                adminModel);
    }


    @DeleteMapping("/admin/{username}")
    public ResponseBody<String> deleteAdmin(
                                        @RequestHeader("Authorization") String token,
                                        @PathVariable String username
                                        ) {
        Logger.info("Delete admin");
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        adminService.deleteAdmin(username, jwtToken);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                "Admin deleted");
    }

}
