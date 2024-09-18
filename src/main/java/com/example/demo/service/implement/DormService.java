package com.example.demo.service.implement;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Dorm;
import com.example.demo.entity.Role;
import com.example.demo.logs.Logger;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.DormRepository;
import com.example.demo.util.convertor.DormConvertor;
import com.example.demo.service.IDormService;

import java.util.UUID;
import java.util.List;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormService implements IDormService {
    
    private final DormRepository dormRepository;

    private final JwtService jwtService;

    public List<DormModel> getAllDorm(JwtToken jwtToken) {
        Logger.info("Get all dorms");
        
        jwtService.allowRoles(jwtToken, Role.ADMIN, Role.TENANT);


        List<Dorm> dorm = dormRepository.findAll();

        return DormConvertor.toModel(dorm);
    }

    public DormModel getDormById(String id,JwtToken jwtToken) {
        Logger.info("Get dorm by id");

        jwtService.allowRoles(jwtToken, Role.ADMIN, Role.TENANT);

        UUID uuid = UUID.fromString(id);
        Dorm dorm = dormRepository.findById(uuid).orElse(null);
        return DormConvertor.toModel(dorm);
    }

    public DormModel createDorm(DormModel dorm,JwtToken jwtToken) {
        Logger.info("Create dorm");

        jwtService.allowRoles(jwtToken, Role.ADMIN);

        Dorm dormEntity = DormConvertor.toEntity(dorm);
        dormEntity = dormRepository.save(dormEntity);

        return DormConvertor.toModel(dormEntity);
    }

    public DormModel updateDorm(String id, DormModel dorm,JwtToken jwtToken) {
        Logger.info("Update dorm");

        jwtService.allowRoles(jwtToken, Role.ADMIN);

        UUID uuid = UUID.fromString(id);

        Dorm dormEntity = DormConvertor.toEntity(dorm);
        dormEntity.setDormID(uuid);
        dormEntity = dormRepository.save(dormEntity);
        return DormConvertor.toModel(dormEntity);
    }

    public void deleteDorm(String id,JwtToken jwtToken) {
        Logger.info("Delete dorm");

        jwtService.allowRoles(jwtToken, Role.ADMIN);

        UUID uuid = UUID.fromString(id);
        dormRepository.deleteById(uuid);
    }

 




}
