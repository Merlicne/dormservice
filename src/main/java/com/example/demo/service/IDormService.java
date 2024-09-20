package com.example.demo.service;

import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;

import java.util.List;

public interface IDormService {
    
    List<DormModel> getAllDorm(JwtToken jwtToken, String includedDeleted);

    DormModel getDormById(String id,JwtToken jwtToken, String includedDeleted);

    DormModel createDorm(DormModel dorm,JwtToken jwtToken);

    DormModel updateDorm(String id, DormModel dorm,JwtToken jwtToken);

    void deleteDorm(String id,JwtToken jwtToken);

}
