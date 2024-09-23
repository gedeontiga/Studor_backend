package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.profilejobprediction.Matter;

import java.util.List;

import com.studor.orientation_student.entities.establishmentsuggestion.Level;

@Repository
public interface MatterRepository extends JpaRepository<Matter, Long> {
    List<Matter> findByCoef(int coef);

    List<Matter> findByNom(String nom);

    List<Matter> findByLevel(Level level);
}
