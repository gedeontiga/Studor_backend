package com.studor.orientation_student.manager.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LoginService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    public Boolean isValidUser(Map<String, Object> userInfo){
        Optional<User> user = userRepository.findByEmail(userInfo.get("email").toString());

        if (user.isPresent() &&
            passwordEncoder.matches(userInfo.get("password").toString(), 
                                    user.get().getMotDePasse())) {
            return true;
        } else {
            return false;
        }
    }
}
