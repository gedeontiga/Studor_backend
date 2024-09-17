package com.studor.orientation_student.manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ManagerServices {
    
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllUserForManager();
    }
}
