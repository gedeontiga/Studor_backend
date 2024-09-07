package com.studor.orientation_student.manager.services;

import java.util.Map;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.Gender;
import com.studor.orientation_student.entities.Profil;
import com.studor.orientation_student.entities.Role;
import com.studor.orientation_student.entities.RoleType;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.Validation;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    
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
        profil.setSexe(Gender.valueOf(sexe.toUpperCase()));
        user.setProfil(profil);
        user = userRepository.save(user);
        validationService.signUp(user);
    }
    
    public void activate(Map<String, Object> activation){
        Validation validation = validationService.checkValidation(activation.get("code").toString());
        if(Instant.now().isAfter(validation.getExpirationInstant())){
            throw new RuntimeException("Your code has expired");
        }
        User activeUser = userRepository.findById(validation.getUser().getId()).orElseThrow(() -> new RuntimeException("Your code has expired"));
        activeUser.setActif(true);
        userRepository.save(activeUser);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }
}
