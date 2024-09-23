package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.profilejobprediction.Options;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {
    Options findByNom(String nom);
}
