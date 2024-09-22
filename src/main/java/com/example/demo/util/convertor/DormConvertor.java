package com.example.demo.util.convertor;

import com.example.demo.entity.Dorm;
import com.example.demo.model.DormModel;

import java.util.ArrayList;
import java.util.List;

public class DormConvertor {
    private DormConvertor() {
        throw new IllegalStateException("Utility class");
    }

    public static DormModel toModel(Dorm entity) {
        return DormModel.builder()
                .dormID(entity.getDormID())
                .name(entity.getName())
                .telephone(entity.getTelephone())
                .address(entity.getAddress())
                .creater(entity.getCreater())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static Dorm toEntity(DormModel model) {
        
        return Dorm.builder()
                .dormID(model.getDormID())
                .name(model.getName())
                .telephone(model.getTelephone())
                .address(model.getAddress())
                .creater(model.getCreater())
                .createdAt(model.getCreatedAt())
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
