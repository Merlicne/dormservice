package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;



import com.example.demo.model.BuildingModel;
import com.example.demo.model.JwtToken;
import com.example.demo.model.ResponseBody;
import com.example.demo.service.IBuildingService;



import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BuildingController {
    private final IBuildingService buildingService;
    
    @GetMapping("/building")
    public ResponseBody<List<BuildingModel>> getBuildingAll(
                                                        @RequestHeader("Authorization") String token,
                                                        @RequestHeader(required = false) String includedDeleted
                                                        ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();


        List<BuildingModel> building = buildingService.getBuildingAll(jwtToken, includedDeleted);
        return new ResponseBody<>(
                            200, 
                            "Success", 
                            building);
    }

    @GetMapping("/building/{id}")
    public ResponseBody<BuildingModel> getBuildingById(
                                                    @PathVariable int id, 
                                                    @RequestHeader("Authorization") String token,
                                                    @RequestHeader(required = false) String includedDeleted
                                                    ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();

        BuildingModel building = buildingService.getBuildingById(id,jwtToken, includedDeleted);
        return new ResponseBody<>(
                        200, 
                        "Success", 
                        building);
    }

    @PostMapping("/building")
    public ResponseBody<BuildingModel> createBuilding(
                                                @RequestBody BuildingModel building, 
                                                @RequestHeader("Authorization") String token
                                                ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        BuildingModel buildingModel = buildingService.createBuilding(building, jwtToken);
        return new ResponseBody<>(
                                200, 
                                "Success", 
                                buildingModel);
    }

    @PutMapping("/building/{id}")
    public ResponseBody<BuildingModel> updateBuilding(
                                                @PathVariable int id, 
                                                @RequestBody BuildingModel building, 
                                                @RequestHeader("Authorization") String token
                                                ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        BuildingModel buildingModel = buildingService.updateBuilding(id, building, jwtToken);
        return new ResponseBody<>(200, "Success", buildingModel);
    }

    @DeleteMapping("/building/{id}")
    public ResponseBody<String> deleteBuilding(
                                            @PathVariable int id, 
                                            @RequestHeader("Authorization") String token
                                            ) {
        token = token.substring(7);
        JwtToken jwtToken = JwtToken.builder().token(token).build();
        buildingService.deleteBuilding(id, jwtToken);
        return new ResponseBody<>(200, "Success", "Deleted");
    }

}

