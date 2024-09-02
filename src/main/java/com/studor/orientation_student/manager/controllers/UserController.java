package com.studor.orientation_student.manager.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.dto.AuthenticationDTO;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/activation")
    public ResponseEntity<Map<String, String>> activate(@RequestBody Map<String, Object> activation) {
        
        Map<String, String> response = new HashMap<String, String>();
        try {
            userService.activate(activation);
            response.put("status", "success");
            response.put("message", "Your account has been activated");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while activating: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDTO authenticationDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password())
        );
        return null;
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Boolean>> getMethodName(@PathVariable String email) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("email exists?", userService.checkIfEmailAlreadyExists(email));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> validSignUp(@RequestBody Map<String, Object> signUpFormValues) {

        Map<String, String> response = new HashMap<String, String>();
        try{
            User user = new User();
            user.setEmail(signUpFormValues.get("email").toString());
            user.setMotDePasse(signUpFormValues.get("password").toString());
            System.out.println(signUpFormValues.get("password").toString());
            user.setNom(signUpFormValues.get("username").toString());
            LocalDate birthDate = LocalDate.parse(signUpFormValues.get("birthDate").toString());
            userService.register(user, signUpFormValues.get("firstName").toString(), 
                                    signUpFormValues.get("lastName").toString(), 
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
}
