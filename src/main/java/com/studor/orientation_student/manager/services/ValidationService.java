package com.studor.orientation_student.manager.services;

import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Service;
import static java.time.temporal.ChronoUnit.MINUTES;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.Validation;
import com.studor.orientation_student.manager.repositories.ValidationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ValidationService {
    
    private ValidationRepository validationRepository;
    private MailNotificationService mailNotificationService;

    public void signUp(User user){
        Validation userValidation = new Validation();
        userValidation.setUser(user);
        Instant creation = Instant.now();
        userValidation.setCreationInstant(creation);
        Instant expiration = creation.plus(10, MINUTES);
        userValidation.setExpirationInstant(expiration);

        Random random = new Random();
        Integer randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        userValidation.setCode(code);
        userValidation = validationRepository.save(userValidation);
        mailNotificationService.send(userValidation);
    }

    public Validation checkValidation(String code){
        return validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Invalid code"));
    }
}
