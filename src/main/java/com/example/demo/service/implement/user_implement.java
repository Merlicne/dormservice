package com.example.demo.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.user_service;
import com.example.demo.model.UserModel;
import com.example.demo.entity.User;

import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class user_implement implements user_service{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserModel> getAllUsers() {
        log.info("Fetching all users from database");
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for(User user: users){
            userModels.add(UserModel.builder()
                                        .id(user.getId())
                                        .name(user.getName())
                                        .build());
        }
        return userModels;
    }

    @Override
    public UserModel saveUser(UserModel user) {
        log.info("Saving user to database");
        User newUser = userRepository.save(User.builder()
                                            .name(user.getName())
                                            .build());
        return UserModel.builder().id(newUser.getId()).name(newUser.getName()).build();
    }
}
