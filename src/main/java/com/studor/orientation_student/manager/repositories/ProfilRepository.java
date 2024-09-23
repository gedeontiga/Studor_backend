package com.studor.orientation_student.manager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.Profil;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.profilejobprediction.Job;

import java.time.LocalDate;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    List<Profil> findByDateDeNaissance(LocalDate dateDeNaissance);

    List<Profil> findByNom(String nom);

    List<Profil> findByReligion(String religion);

    Profil findByUser(User user);

    List<Profil> findByJob(Job job);

    List<Profil> findBySexe(String sexe);
}
