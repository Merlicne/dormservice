package com.example.demo.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.entity.Dorm;
import com.example.demo.enums.Role;
import com.example.demo.exception.NotFoundException;
import com.example.demo.logs.Logger;
import com.example.demo.middleware.JwtService;
import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;
import com.example.demo.repository.DormRepository;
import com.example.demo.util.convertor.DormConvertor;
import com.example.demo.util.role_authorization.RoleValidation;
import com.example.demo.service.IDormService;

import java.util.List;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormService implements IDormService {
    
    private final DormRepository dormRepository;

    private final JwtService jwtService;

    public DormModel getLatestDorm(JwtToken jwtToken) {
        Logger.info("get latest dorm update");
        
        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN, Role.TENANT);

        Dorm dorm = dormRepository.findLatestDorm().orElseThrow(() -> new NotFoundException("Dorm not found"));
        

        return DormConvertor.toModel(dorm);
    }
    
    public List<DormModel> getUpdatedHistory(JwtToken jwtToken) {
        Logger.info("get updated history");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        List<Dorm> dorms = dormRepository.findUpdatedHistory();
        return DormConvertor.toModel(dorms);
    }

    @Transactional
    public DormModel createDorm(DormModel dorm,JwtToken jwtToken) {
        Logger.info("Create dorm");

        Role role = jwtService.extractRole(jwtToken.getToken());
        RoleValidation.allowRoles(role, Role.ADMIN);

        Dorm dormEntity = DormConvertor.toEntity(dorm);

        String creater = jwtService.extractUsername(jwtToken.getToken());
        dormEntity.setCreater(creater);
        dormEntity = dormRepository.save(dormEntity);

        return DormConvertor.toModel(dormEntity);
    }


}
