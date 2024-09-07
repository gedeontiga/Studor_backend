package com.studor.orientation_student.manager.security;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtService {

    private final UserService userService;

    public Map<String, String> generate(String email){
        User user = userService.loadUserByUsername(email);
        return this.generateJwt(user);
    }

    public Map<String, String> generateJwt(User user){
        return null;
    }
}