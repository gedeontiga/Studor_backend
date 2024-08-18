package com.studor.orientation_student.manager.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.RegisterService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/sign-up")
public class RegisterController {

    @Autowired
    private RegisterService signUpService;

    @GetMapping("/check-email/{email}")
    public boolean getMethodName(@PathVariable String email) {
        return signUpService.checkIfEmailAlreadyExists(email);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> validSignUp(@RequestBody Map<String, Object> signUpFormValues) {

        Map<String, String> response = new HashMap<String, String>();
        try{
            User user = new User();
            user.setEmail(signUpFormValues.get("email").toString());
            user.setMotDePasse(signUpFormValues.get("password").toString());
            user.setNom(signUpFormValues.get("username").toString());
            LocalDate birthDate = LocalDate.parse(signUpFormValues.get("birthDate").toString());
            signUpService.register(user, signUpFormValues.get("firstname").toString(), 
                                    signUpFormValues.get("lastname").toString(), 
                                    signUpFormValues.get("sexe").toString(), birthDate);

            response.put("status", "success");
            response.put("message", "Registration successful");
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            response.put("status", "error");
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);  // 400 Bad Request status code
        }
    }

    @PostMapping("/activation")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
