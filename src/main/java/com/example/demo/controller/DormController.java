package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.DormModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.ResponseBody;
import com.example.demo.service.IDormService;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DormController {
    
    private final IDormService dormService;



    @GetMapping("/dorm")
    public ResponseBody<DormModel> getDormInfo(
                                                @RequestHeader("Authorization") String token 
                                            ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        DormModel dorm = dormService.getLatestDorm(jwtToken);
        return new ResponseBody<>(
                            200, 
                            "Success", 
                            dorm);
        
    }

    @GetMapping("/dorm/history")
    public ResponseBody<List<DormModel>> getDormHistory(
                                                        @RequestHeader("Authorization") String token
                                                        ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        List<DormModel> dorm = dormService.getUpdatedHistory(jwtToken);
        return new ResponseBody<>(
                            200, 
                            "Success", 
                            dorm);
    }

    @PostMapping("/dorm")
    public ResponseBody<DormModel> createDorm(
                                            @RequestBody DormModel dorm, 
                                            @RequestHeader("Authorization") String token
                                            ) {

        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        DormModel dormModel = dormService.createDorm(dorm, jwtToken);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                dormModel);
    }



}
