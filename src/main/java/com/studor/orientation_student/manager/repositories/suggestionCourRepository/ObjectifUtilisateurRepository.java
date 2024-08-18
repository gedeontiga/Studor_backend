package com.studor.orientation_student.manager.repositories.suggestionCourRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.studor.orientation_student.entities.suggestionCourEntities.ObjectifProfil;
// import java.util.List;

public interface ObjectifUtilisateurRepository extends JpaRepository<ObjectifProfil, Long> {
    ObjectifProfil findByDescriptionAndMoyenneAndStatuVise(String description, Double moyenne, String statuVise);
}
