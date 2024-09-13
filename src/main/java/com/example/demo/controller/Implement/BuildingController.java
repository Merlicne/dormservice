package com.example.demo.controller.Implement;


// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.IBuildingController;
import com.example.demo.entity.Building;
import com.example.demo.service.IBuildingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BuildingController implements IBuildingController{
    private final IBuildingService buildingService;
    // private static final Logger logger = LoggerFactory.getLogger(BuildingController.class);
    @GetMapping("/test")
    public String test() {
        return "Test 123";
    }
    
    @GetMapping("/building")
    public @ResponseBody Iterable<Building> getBuildingAll() {
        return buildingService.getBuildingAll();
    }

    @GetMapping("/building/{id}")
    public @ResponseBody Building getBuildingById(@PathVariable int id) {
        return buildingService.getBuildingById(id);
    }

    @PostMapping("/building")
    public Building createBuilding(@RequestBody Building building) {
        // logger.info("Received input: {}", building);
        return buildingService.createBuilding(building);
        // return ResponseEntity.ok().body(building);
    }

    @PutMapping("/building/{id}")
    public @ResponseBody Building updateBuilding(@PathVariable int id, @RequestBody Building building) {
        return buildingService.updateBuilding(id, building);
    }

    @DeleteMapping("/building/{id}")
    public void deleteBuilding(@PathVariable int id) {
        buildingService.deleteBuilding(id);
    }
}

