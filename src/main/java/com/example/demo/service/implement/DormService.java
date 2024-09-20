package com.example.demo.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.entity.Dorm;
import com.example.demo.entity.Role;
import com.example.demo.exception.NotFoundException;
import com.example.demo.logs.Logger;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.DormRepository;
import com.example.demo.util.convertor.DormConvertor;
import com.example.demo.util.role_authorization.RoleValidation;
import com.example.demo.util.validator.RestParamValidator;
import com.example.demo.service.IDormService;


import java.util.UUID;
import java.util.List;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormService implements IDormService {
    
    private final DormRepository dormRepository;

    private final JwtService jwtService;

    public List<DormModel> getAllDorm(JwtToken jwtToken, String includedDeleted) {
        Logger.info("Get all dorms");
        
        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        includedDeleted =RestParamValidator.validateIncludedDeleted(includedDeleted);

        List<Dorm> dorm;
        if (includedDeleted.equalsIgnoreCase("true")){
            dorm = dormRepository.findAll();
        }else{
            dorm = dormRepository.findNotDeletedAll();
        }   

        return DormConvertor.toModel(dorm);
    }

    public DormModel getDormById(String id,JwtToken jwtToken, String includedDeleted) {
        Logger.info("Get dorm by id");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        UUID uuid = UUID.fromString(id);

        includedDeleted =RestParamValidator.validateIncludedDeleted(includedDeleted);


        Dorm dorm;
        if (includedDeleted.equalsIgnoreCase("true")){
            dorm = dormRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Dorm not found"));
        }else{
            dorm = dormRepository.findNotDeletedById(uuid).orElseThrow(() -> new NotFoundException("Dorm not found"));
        }
        return DormConvertor.toModel(dorm);
    }

    @Transactional
    public DormModel createDorm(DormModel dorm,JwtToken jwtToken) {
        Logger.info("Create dorm");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        Dorm dormEntity = DormConvertor.toEntity(dorm);
        dormEntity = dormRepository.save(dormEntity);

        return DormConvertor.toModel(dormEntity);
    }

    @Transactional
    public DormModel updateDorm(String id, DormModel dorm,JwtToken jwtToken) {
        Logger.info("Update dorm");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        UUID uuid = UUID.fromString(id);
        Dorm oldDorm = dormRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Dorm not found"));
        
        dorm.setDormID(uuid);
        Dorm dormEntity = DormConvertor.toEntity(dorm);

        dormEntity.setCreatedAt(oldDorm.getCreatedAt());
        dormEntity.setDeletedAt(oldDorm.getDeletedAt());
        dormEntity = dormRepository.save(dormEntity);
        return DormConvertor.toModel(dormEntity);
    }

    @Transactional
    public void deleteDorm(String id,JwtToken jwtToken) {
        Logger.info("Delete dorm");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        dormRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Dorm not found"));

        UUID uuid = UUID.fromString(id);
        dormRepository.deleteById(uuid);
    }

 




}
