package com.studor.orientation_student.manager.repositories.suggestionCourRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.suggestionCourEntities.ModelSuggestionCour;

@Repository
public interface ModelSuggestionCourRepository extends JpaRepository<ModelSuggestionCour, Long>{
    
}
