package com.example.demo.util.convertor;

import com.example.demo.entity.Dorm;
import com.example.demo.model.DormModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DormConvertor {
    private DormConvertor() {
        throw new IllegalStateException("Utility class");
    }

    public static DormModel toModel(Dorm entity) {

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

        return DormModel.builder()
                .dormID(entity.getDormID())
                .name(entity.getName())
                .telephone(entity.getTelephone())
                .address(entity.getAddress())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public static Dorm toEntity(DormModel model) {
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

        return Dorm.builder()
                .dormID(model.getDormID())
                .name(model.getName())
                .telephone(model.getTelephone())
                .address(model.getAddress())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public static List<DormModel> toModel(List<Dorm> entities) {
        List<DormModel> models = new ArrayList<>();
        for (Dorm entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }

    public static List<Dorm> toEntity(List<DormModel> models) {
        List<Dorm> entities = new ArrayList<>();
        for (DormModel model : models) {
            entities.add(toEntity(model));
        }
        return entities;
    }
}
