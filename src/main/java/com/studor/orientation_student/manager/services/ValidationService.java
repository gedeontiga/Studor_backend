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
    
    private ValidationRepository userValidationRepository;
    private MailNotificationService mailNotificationService;

    public void register(User user){
        Validation userValidalidation = new Validation();
        userValidalidation.setUser(user);
        Instant creation = Instant.now();
        userValidalidation.setCreationInstant(creation);
        Instant expiration = creation.plus(10, MINUTES);
        userValidalidation.setExpirationInstant(expiration);

        Random random = new Random();
        Integer randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        userValidalidation.setCode(code);
        userValidalidation = userValidationRepository.save(userValidalidation);
        mailNotificationService.send(userValidalidation);
    }
}
