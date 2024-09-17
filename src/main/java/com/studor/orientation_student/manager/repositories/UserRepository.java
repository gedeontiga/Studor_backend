package com.studor.orientation_student.manager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByNom(String nom);

    User findByMotDePasse(String motDePasse);

    User findByMotDePasseAndEmail(String pass, String email);

    @Query("FROM User user WHERE user.role.type <> 'ADMIN'")
    List<User> findAllUserForManager();
}
