package com.studor.orientation_student.manager.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.Validation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MailNotificationService {
    
    JavaMailSender javaMailSender;

    public void sendMail(Validation validation){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@studor.com");
        mailMessage.setTo(validation.getUser().getEmail());
        mailMessage.setSubject("Votre code d'activation");

        String text = String.format(
            "Salut %s, \n votre code d'activation est %s.\n Merci d'utiliser l'application StudOr et a bientot.",
            validation.getUser().getNom(), 
            validation.getCode()
            );
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}
