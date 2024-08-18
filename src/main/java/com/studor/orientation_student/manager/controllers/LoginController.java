package com.studor.orientation_student.manager.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studor.orientation_student.manager.services.LoginService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @PostMapping("/submit")
    public boolean login(@RequestBody Map<String, Object> loginForm) {
        return loginService.isValidUser(loginForm);
    }
}
