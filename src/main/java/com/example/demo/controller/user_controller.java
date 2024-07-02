package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserModel;
import com.example.demo.service.user_service;
// import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/user")
@RestController
public class user_controller {
    @Autowired
    private user_service userService;

    @GetMapping("/")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().toString());
    }

    @PostMapping("/")
    public ResponseEntity<String> saveUser(@RequestBody UserModel user) {
        UserModel userModel = UserModel.builder()
                                    .name(user.getName())
                                    .build();
        return ResponseEntity.ok(userService.saveUser(userModel).toString());
    }

    /*
     * CURL command to test the API
     * curl -X POST -H "Content-Type: application/json" -d '{"name":"John Doe"}' http://localhost:8080/user/
     * curl -X GET http://localhost:8080/user/
     */
    
    // @GetMapping("/")
    // public ResponseEntity<String> getAllUsers() {
    //     return ResponseEntity.ok("Hello World");
    // }
    
}
