package com.studor.orientation_student.manager.services;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.Profil;
import com.studor.orientation_student.entities.Role;
import com.studor.orientation_student.entities.RoleType;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegisterService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public boolean checkIfEmailAlreadyExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }
    
    public void register(User user, String nom, String prenom, String sexe, LocalDate birthDate){
        String encryptedPassword = this.passwordEncoder.encode(user.getMotDePasse());
        user.setMotDePasse(encryptedPassword);
        Role role = new Role();
        role.setType(RoleType.USER);
        user.setRole(role);
        Profil profil = new Profil();
        profil.setNom(nom);
        profil.setPrenom(prenom);
        profil.setDateDeNaissance(birthDate);
        profil.setSexe(sexe);
        user.setProfil(profil);
        user = userRepository.save(user);
        validationService.signUp(user);
    }
}
