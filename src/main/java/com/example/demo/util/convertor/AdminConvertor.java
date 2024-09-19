package com.example.demo.util.convertor;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Admin;
import com.example.demo.model.AdminModel;

public class AdminConvertor {
    
    private AdminConvertor() {
        throw new IllegalStateException("Utility class");   
    }

    public static Admin toEntity(AdminModel model) {


        return com.example.demo.entity.Admin.builder()
                .username(model.getUsername())
                .password(model.getPassword())
                .name(model.getName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .deletedAt(model.getDeletedAt())
                .build();
    }

    public static AdminModel toModel(Admin entity) {
        return com.example.demo.model.AdminModel.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static List<AdminModel> toModel(List<Admin> entities) {
        List<AdminModel> models = new ArrayList<>();
        for (Admin entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static List<Admin> toEntity(List<AdminModel> models) {
        List<Admin> entities = new ArrayList<>();
        for (AdminModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
