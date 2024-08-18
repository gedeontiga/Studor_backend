package com.studor.orientation_student.manager.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.manager.services.ActivationService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ActivationController {

    @Autowired
    private ActivationService activationService;
    
    @PostMapping("/activation")
    public ResponseEntity<Map<String, String>> activate(@RequestBody Map<String, Object> activation) {
        
        Map<String, String> response = new HashMap<String, String>();
        try {
            activationService.activate(activation);
            response.put("status", "success");
            response.put("message", "Your account has been activated");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while activating: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
}
