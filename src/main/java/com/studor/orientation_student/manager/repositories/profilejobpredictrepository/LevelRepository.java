package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.profilejobprediction.Level;
import com.studor.orientation_student.entities.profilejobprediction.Subdomain;

import java.util.List;


@Repository
public interface LevelRepository extends JpaRepository<Level, Long>{
    Level findByCode(String code);
    List<Level> findByOption(Subdomain option);
}
