package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminModel {
    
    @JsonAlias("username")
    private String username;
    @JsonAlias("password")
    private String password;
    @JsonAlias("name")
    private String name;
    @JsonAlias("email")
    private String email;
    @JsonAlias("phone")
    private String phone;
    @JsonAlias("created_at")
    private String createdAt;
    @JsonAlias("updated_at")
    private String updatedAt;
    @JsonAlias("deleted_at")
    private String deletedAt;
}
