package com.studor.orientation_student.manager.repositories.suggestionCourRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studor.orientation_student.entities.suggestionCourEntities.Programme;
// import java.util.List;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    Programme findByDescriptionAndDifficulte(String description, String difficulte);
}
