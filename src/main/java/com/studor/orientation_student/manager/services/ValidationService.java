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
        Validation validation = new Validation();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreationInstant(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpirationInstant(expiration);

        Random random = new Random();
        Integer randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);
        validation = validationRepository.save(validation);
        mailNotificationService.sendMail(validation);
    }

    public void updateValidationInstant(Validation validation){
        validation.setActivationInstant(Instant.now());
        validationRepository.save(validation);
    }

    public Validation checkValidation(String code){
        return validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Invalid code"));
    }

    public void deleteValidation(Validation validation) {
        validationRepository.delete(validation);
    }
}
