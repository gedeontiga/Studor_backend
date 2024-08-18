package com.studor.orientation_student.manager.services;

import java.time.Instant;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.Validation;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ActivationService {
    
    private ValidationService validationService;
    private UserRepository userRepository;

    public void activate(Map<String, Object> activation){
        Validation validation = validationService.checkValidation(activation.get("code").toString());
        if(Instant.now().isAfter(validation.getExpirationInstant())){
            throw new RuntimeException("Your code has expired");
        }
        User activeUser = userRepository.findById(validation.getUser().getId()).orElseThrow(() -> new RuntimeException("Your code has expired"));
        activeUser.setActif(true);
        userRepository.save(activeUser);
    }
}
