package com.studor.orientation_student.manager.repositories.establishmentsuggestcontroller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studor.orientation_student.entities.establishmentsuggestion.Training;

import java.util.List;
import com.studor.orientation_student.entities.profilejobprediction.Job;


public  interface TrainingRepository extends JpaRepository<Training, Long>{

    List<Training> findByCout(double cout);
    List<Training> findByDuree(int duree);
    Training findByNom(String nom);
    Training findByJob(Job job);
}
