package com.studor.orientation_student.manager.controllers.apis;

import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.AdminServices;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminServices adminServices;

    @PreAuthorize("hasAuthority('ADMIN_READ_ALL')")
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return adminServices.getAllUsers();
    }
}