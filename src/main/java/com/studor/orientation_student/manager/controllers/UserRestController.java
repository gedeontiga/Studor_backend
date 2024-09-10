package com.studor.orientation_student.manager.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.dto.AuthenticationDTO;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.security.JwtService;
import com.studor.orientation_student.manager.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    
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
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password())
        );
        if (authenticate.isAuthenticated()) {
            return jwtService.generate(authenticationDTO.email());
        }
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        jwtService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody Map<String, Object> formValue) {
        userService.forgotPassword(formValue.get("email").toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new-password")
    public ResponseEntity<Void> newPassword(@RequestBody Map<String, Object> formValue){
        userService.newPassword(formValue);
        return ResponseEntity.ok().build();
    }
    

    @GetMapping("/check-email/{email}")
    public Boolean isValidEmail(@PathVariable String email) {
        return userService.checkIfEmailAlreadyExists(email);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> validSignUp(@RequestBody Map<String, Object> signUpFormValues) {

        Map<String, String> response = new HashMap<String, String>();
        try{
            User user = new User();
            user.setEmail(signUpFormValues.get("email").toString());
            user.setMotDePasse(signUpFormValues.get("password").toString());
            user.setNom(signUpFormValues.get("username").toString());
            LocalDate birthDate = LocalDate.parse(signUpFormValues.get("birthDate").toString());
            userService.register(user, signUpFormValues.get("firstName").toString(), 
                                    signUpFormValues.get("lastName").toString(), 
                                    signUpFormValues.get("gender").toString(), birthDate);

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
