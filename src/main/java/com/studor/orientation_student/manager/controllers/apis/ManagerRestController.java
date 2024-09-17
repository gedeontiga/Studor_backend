package com.studor.orientation_student.manager.controllers.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.ManagerServices;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/manager")
public class ManagerRestController {

    private final ManagerServices managerServices;
    
    @PreAuthorize("hasAuthority('MANAGER_READ_ALL_USERS')")
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return managerServices.getAllUsers();
    }
    
}
