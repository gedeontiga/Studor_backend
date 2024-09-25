package com.studor.orientation_student.manager.services;

import java.util.List;
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
import com.studor.orientation_student.manager.repositories.HobbiesRepository;
import com.studor.orientation_student.manager.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final HobbiesRepository hobbiesRepository;

    public boolean checkIfEmailAlreadyExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void register(User user, String nom, String prenom, String sexe, 
            String loisir1, String loisir2, LocalDate birthDate){
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
        profil.setHobbies(List.of(
            hobbiesRepository.findByNom(loisir1).get(),
            hobbiesRepository.findByNom(loisir2).get()
        ));

        user.setProfil(profil);
        user = userRepository.save(user);
        validationService.signUp(user);
    }

    public void activate(Map<String, Object> activation){
        User user = userRepository.findByEmail(activation.get("email").toString()).orElseThrow(() -> new RuntimeException("Validation not found"));
        Validation validation = validationService.checkValidation(user, activation.get("code").toString());
        if((Instant.now().isAfter(validation.getExpirationInstant())) && !user.getActif()){
            throw new RuntimeException("Your code has expired");
        }
        user.setActif(true);
        userRepository.save(user);
        validationService.updateValidationInstant(validation);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    public void forgotPassword(String email) {
        User user = this.loadUserByUsername(email);
        validationService.signUp(user);
    }

    public void newPassword(Map<String, Object> formValues) {
        User user = this.loadUserByUsername(formValues.get("email").toString());
        final Validation validation = validationService.checkValidation(user, formValues.get("code").toString());
        if (validation.getUser().getEmail().equals(user.getEmail())) {
            String encryptedPassword = this.passwordEncoder.encode(formValues.get("password").toString());
            user.setMotDePasse(encryptedPassword);
            userRepository.save(user);
        }
    }
}
