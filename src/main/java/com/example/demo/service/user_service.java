package com.example.demo.service;

import java.util.List;

import com.example.demo.model.UserModel;

public interface user_service {
    
    List<UserModel> getAllUsers();

    // save user
    UserModel saveUser(UserModel user);
}
