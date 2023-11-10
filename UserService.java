package com.project.service;

import org.springframework.stereotype.Service;

import com.project.model.User;
import com.project.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}