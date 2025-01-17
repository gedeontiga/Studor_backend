package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.establishmentsuggestion.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByCode(String code);
}
