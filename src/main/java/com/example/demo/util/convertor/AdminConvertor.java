package com.example.demo.util.convertor;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Admin;
import com.example.demo.model.AdminModel;

public class AdminConvertor {
    
    private AdminConvertor() {
        throw new IllegalStateException("Utility class");   
    }

    public static Admin toEntity(AdminModel model) {
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        LocalDateTime deletedAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (model.getCreatedAt() != null) {
            createdAt = LocalDateTime.parse(model.getCreatedAt(), formatter);
        }
        if (model.getUpdatedAt() != null) {
            updatedAt = LocalDateTime.parse(model.getUpdatedAt(), formatter);
        }
        if (model.getDeletedAt() != null) {
            deletedAt = LocalDateTime.parse(model.getDeletedAt(), formatter);
        }


        return com.example.demo.entity.Admin.builder()
                .username(model.getUsername())
                .password(model.getPassword())
                .name(model.getName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public static AdminModel toModel(Admin entity) {
        String createdAt = null;
        String updatedAt = null;
        String deletedAt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (entity.getCreatedAt() != null) {
            createdAt = entity.getCreatedAt().format(formatter);
        }
        if (entity.getUpdatedAt() != null) {
            updatedAt = entity.getUpdatedAt().format(formatter);
        }
        if (entity.getDeletedAt() != null) {
            deletedAt = entity.getDeletedAt().format(formatter);
        }
        return com.example.demo.model.AdminModel.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
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
